package openehr.basic

//import com.cabolabs.openehr.adl.ArchetypeManager
//import org.openehr.rm.common.archetyped.Locatable
import repo.datavalue.*
import repo.*

import com.cabolabs.openehr.opt.parser.OperationalTemplateParser
import com.cabolabs.openehr.opt.instance_validation.XmlValidation

import com.cabolabs.openehr.opt.model.ObjectNode
import com.cabolabs.openehr.opt.model.domain.CDvQuantity
import com.cabolabs.openehr.opt.model.OperationalTemplate
import com.cabolabs.openehr.opt.model.PrimitiveObjectNode

import com.cabolabs.openehr.opt.manager.OptManager
import com.cabolabs.openehr.opt.manager.OptRepository
import com.cabolabs.openehr.opt.manager.OptRepositoryFSImpl

import com.cabolabs.openehr.rm_1_0_2.data_types.text.DvText
import com.cabolabs.openehr.rm_1_0_2.data_types.text.DvCodedText
import com.cabolabs.openehr.rm_1_0_2.data_types.quantity.DvCount
import com.cabolabs.openehr.rm_1_0_2.data_types.quantity.DvQuantity
import com.cabolabs.openehr.rm_1_0_2.data_types.quantity.DvProportion
import com.cabolabs.openehr.rm_1_0_2.data_types.quantity.date_time.DvDateTime

import com.cabolabs.openehr.validation.RmValidationReport
import com.cabolabs.openehr.opt.model.validation.ValidationResult

import grails.converters.*

// TODO: move to service
import com.cabolabs.openehr.rest.client.OpenEhrRestClient

class RecordsController {

    def dataBindingService
    def integrationService

    private static String PS = File.separator
    private static String path = "."+ PS +"archetypes"
    private static List datavalues = [
        'DV_TEXT', 'DV_CODED_TEXT', 'DV_QUANTITY', 'DV_COUNT',
        'DV_ORDINAL', 'DV_DATE', 'DV_DATE_TIME', 'DV_PROPORTION',
        'DV_DURATION']

    def index()
    {
        // TODO: view
    }

    def create_vitals()
    {
    }

    def save_vitals()
    {
        //println params
        //println request.JSON // content type is not json

        def template_id = request.JSON.template_id


        // TODO: move to service

        // String opt_repo_path = '.'
        // OptRepository repo = new OptRepositoryFSImpl(opt_repo_path)

        // OptManager opt_manager = OptManager.getInstance()
        // opt_manager.init(repo)

        // String namespace = 'opts'

        // def opt = opt_manager.getOpt(template_id, namespace)
        def opt = integrationService.getTemplate(template_id)
        if (!opt)
        {
            return 'opt not found'
        }


        // 1. Group by template path and add the attribute name to each value and binding info

        // Map: Parent TemplatePath -> Values (first grouping)
        def data_grouping = request.JSON.values.groupBy{ it.tpath.substring(0, it.tpath.lastIndexOf('/')) }

        data_grouping = data_grouping.collectEntries { template_path, values_and_bindings ->

            def attrs_add = []
            values_and_bindings.each {
                attrs_add << it.tpath.substring(it.tpath.lastIndexOf('/') +1) // attribute name from template path
            }

            def attr_values = []
            values_and_bindings.eachWithIndex { vav_item, i ->
                attr_values[i] = vav_item << [attr: attrs_add[i]]
            }

            [template_path, attr_values]
        }

        def constraints

        def validation_errors = new RmValidationReport()
        def rm_dv

        data_grouping.each { template_path, values_and_bindings ->

            //println template_path

            constraints = opt.getNodesByTemplatePath(template_path)

            // FIXME: if there are many alternative types, we need to match one by the attributes present in the values
            // need to use a matcher to get the right constraint alternative!
            //println constraints
            //println constraints.rmTypeName



            // 2. rm data binding for automatic validation
            switch (constraints[0].rmTypeName)
            {
                case 'DV_QUANTITY':
                    rm_dv = new DvQuantity()

                    def magnitude_value_binding = values_and_bindings.find{ it.attr == 'magnitude' }
                    if (magnitude_value_binding)
                    {
                        magnitude_value_binding.type = 'double' // for casting the string to double
                    }
                    // else: this is not valid because is missing a required DV value
                break
                case 'DV_COUNT':
                    rm_dv = new DvCount()

                    def magnitude_value_binding = values_and_bindings.find{ it.attr == 'magnitude' }
                    if (magnitude_value_binding)
                    {
                        magnitude_value_binding.type = 'integer' // for casting the string to double
                    }
                    // else: this is not valid because is missing a required DV value
                break
                case 'DV_TEXT':
                    rm_dv = new DvText()
                break
                // TODO: check I think this will be CODE_PHRASE
                case 'DV_CODED_TEXT':
                    rm_dv = new DvCodedText()
                break
                case 'DV_DATE_TIME':
                    rm_dv = new DvDateTime()
                break
                case 'DV_PROPORTION':
                    rm_dv = new DvProportion()

                    def numerator_value_binding = values_and_bindings.find{ it.attr == 'numerator' }
                    if (numerator_value_binding)
                    {
                        numerator_value_binding.type = 'float' // for casting the string to double
                    }

                    def denominator_value_binding = values_and_bindings.find{ it.attr == 'denominator' }
                    if (denominator_value_binding)
                    {
                        denominator_value_binding.type = 'float' // for casting the string to double
                    }
                break
                default:
                   println constraints[0].rmTypeName +" is not supported yet"
            }


            // FIXME: for coded text que hace structured attributes, this won't work
            values_and_bindings.each {
                if (it.type) // need to cast
                {
                    switch (it.type)
                    {
                        case 'double':
                           it.value = it.value.toDouble()
                        break
                        case 'integer':
                           it.value = it.value.toInteger()
                        break
                        case 'float':
                           it.value = it.value.toFloat()
                        break
                        default:
                            println "Cast type set to '${it.type}' but not it's not supported yet"
                    }
                }
                rm_dv."${it.attr}" = it.value
            }



            // 3. data value validation
            if (constraints[0] instanceof ObjectNode) // ObjectNode doesn't have an isValid() method
            {
                def method = "validate${constraints[0].rmTypeName}"
                def validation_result =  this."$method"(rm_dv, constraints[0])

                if (!validation_result)
                {
                    validation_errors.addError(
                        template_path,
                        validation_result.message
                    )
                }
            }


            values_and_bindings << [
                dv_data: rm_dv
            ]
        }


        validation_errors.errors.each {
            println it
        }

        if (!validation_errors.hasErrors())
        {
            def document = new Document(
                author:      Clinician.get(1), // The clinician should come from a session, for that we need to add a login
                templateId:  template_id, 
                archetypeId: opt.definition.archetypeId,
                ehr:         Ehr.get(1) // NOTE: the EHR should be selected by the user, we don't have a UI to do that yet
            )

            document.save(failOnError: true)



            // NOTE: this will create a Structure for the COMPOSITION too
            //def item = bindContents(document, data_grouping, opt.definition, true)

            // This code avoids to create the Structure for the COMPOSITION
            def content_item_attrs = opt.definition.attributes.findAll{ it.rmAttributeName == 'content' }
            def item
            content_item_attrs.each { content_item_attr ->
                content_item_attr.children.each { content_item ->

                    // TODO: filter data_grouping by the path of the content_item
                    item = bindContents(document, data_grouping, content_item, 'content', true)
                    item.save(failOnError: true)
                }
            }

            // TODO: also need a structure for the context

            //println item as JSON
            //item.save(failOnError: true) // FIXME: there is a problem saving




            // ==============================================
            // Preparte CDR commit
            // ==============================================
            // integration with openEHR CDR
            def compo = integrationService.buildComposition(document)
            //def json_compo = integrationService.serializeComposition(compo) // TEST




            // TEST
            // TODO: move to service
            def api_url = 'http://localhost:8090/openehr/v1'
            def api_auth_url = 'http://localhost:8090/rest/v1'
            def api_admin_url = 'http://localhost:8090/adminRest'

            def client = new OpenEhrRestClient(
                api_url,
                api_auth_url,
                api_admin_url
            )

            // set required header for POST endpoints
            client.setCommitterHeader('name="'+ document.author.name +'", external_ref.id="'+ document.author.uid +'", external_ref.namespace="demographic", external_ref.type="PERSON"')

            client.auth("admin@cabolabs.com", "admin") // TODO: set on config file


            def p_ehr = document.ehr // NOTE: the EHR should be selected by the user, we don't have a UI to do that yet
            
            // 1. get EHR by uid
            // TEST: is this null or returns some error code?
            def ehr = client.getEhr(p_ehr.uid)


            // 2. if EHR doesn't exists, create with uid (PUT)
            if (!ehr)
            {
                ehr = client.createEhr(p_ehr.uid)

                if (!ehr)
                {
                    // TODO: handle!
                    throw new Exception("EHR wasn't created in the CDR")
                }
            }


            // 3. upload opt (to be sure it's in the CDR)
            String opt_contents = integrationService.getTemplateContents(template_id)
            client.uploadTemplate(opt_contents)


            // 4. commit compo to EHR
            def composition = client.createComposition(p_ehr.uid, compo)

            if (!composition)
            {
                println client.lastError
                throw new Exception("Composition wasn't created in the CDR")
            }
        }



        render(status:201, text:validation_errors as JSON, contentType:"application/json", encoding:"UTF-8")
        return


        // 4. mapping to local document and store
        // need to transform flat routes in a tree
        // note this is done here using the AOM, we need to use the TOM
        // https://github.com/ppazos/openEHR-skeleton/blob/master/grails-app/services/com/cabolabs/openehr/skeleton/data/DataBindingService.groovy
    }

    // TODO: this doesn't bind rm attributes that are not in the opt
    // top = true means the item is a direct child of document
    private def bindContents(document, data_grouping, opt_node, rm_attr, top = false)
    {
        //println rm_attr +" "+ opt_node.rmTypeName

        def item
        if (opt_node.rmTypeName == 'ELEMENT')
        {
            item = new Element(
                type:        opt_node.rmTypeName,
                archetypeId: opt_node.getOwnerArchetypeId(),
                path:        opt_node.templatePath, //.path, // FIXME: if the node is an constraint ref, use the reference
                nodeId:      opt_node.nodeId,
                parent:      document,
                name:        new repo.datavalue.DvText(
                    value: opt_node.text
                ),
                attr:        rm_attr
            )

            // FIXME: if there are multiple children alternatives, match the right alternative by the available
            //        attribute names instead of using the first one (children[0])
            item.value = bindDv(document, data_grouping, opt_node.attributes.find{ it.rmAttributeName == 'value'}.children[0] )
        }
        else
        {
            item = new Structure(
                type:          opt_node.rmTypeName,
                archetypeId:   opt_node.getOwnerArchetypeId(),
                path:          opt_node.templatePath, //.path, // FIXME: if the node is an constraint ref, use the reference
                nodeId:        opt_node.nodeId,
                parent:        document,
                attr:          rm_attr,
                top:           top,
                archetypeRoot: (opt_node.archetypeId ? true : false) // is this an archetype root?
            )

            def attrs = []
            def contents
            opt_node.attributes.each { attr_node ->
                attr_node.children.each { child_node -> // alternative types if attr node is single, multiple memers if attr is multiple
                    
                    // FIXME: only bind if there are matching data_groups for the child_node path
                    //attrs << bindContents(document, data_grouping, child_node)
                    contents = bindContents(document, data_grouping, child_node, attr_node.rmAttributeName)
                    if (contents)
                    {
                        //println "contents: "+ contents
                        item.addToItems(contents)
                    }
                }
            }
        }

        if (!item.validate())
        {
            println item.errors
        }

        return item
    }

    def bindDv(document, data_grouping, opt_node)
    {
        //println 'bindDv '+ opt_node.templatePath
        //println data_grouping.find{ it.key == opt_node.templatePath }.findResults { it.value.dv_data }
        // it.value is a list of bidings and data and one should be the dv_data already binded to the RM dv
        def dv_data = data_grouping.collect{ if (it.key == opt_node.templatePath) return it.value.find { binding -> binding.dv_data != null }?.dv_data }.find{ it != null }

        def persistent_dv
        if (dv_data)
        {
            switch (dv_data)
            {
                case { it instanceof DvQuantity}:
                    persistent_dv = new repo.datavalue.DvQuantity(
                        magnitude: BigDecimal.valueOf(dv_data.magnitude),
                        units: dv_data.units
                    )
                break
                case { it instanceof DvProportion}:
                    persistent_dv = new repo.datavalue.DvProportion(
                        numerator: new BigDecimal(Float.toString(dv_data.numerator)),
                        denominator: new BigDecimal(Float.toString(dv_data.denominator))
                    )
                break
                case { it instanceof DvText}:
                    persistent_dv = new repo.datavalue.DvText(
                        value: dv_data.value
                    )
                break
                // TODO: support more types
            }
        }

        //println persistent_dv

        return persistent_dv
    }


    def create_blood_pressure()
    {
    }
    
    def save_blood_pressure()
    {
        if (!params.save)
        {
            redirect action: 'create_blood_pressure'
            return
        }
        
        /*
        def loader = ArchetypeManager.getInstance(path)
        loader.loadAll()
        
        def archetype = loader.getArchetype(params.archetypeId)
        
        assert archetype.archetypeId.value == params.archetypeId
        
        
        //   println "archetype paths"
        //   archetype.physicalPaths().sort{ it }.each {
        //     println it
        //   }
        //   println ""
        
        //   println "params"
        //   params.sort{it.key}.each { path, value ->
        
        //     // is a path?
        //     if (!path.startsWith('/')) return
            
        //     println path +": "+ value
        //   }
        //   println ""
        


        // ===============================================================================
        // Data processing / preparation
        
        println "data grouper creator"
        def data_grouper = [:]
        def constraint, parent_path, attribute
        params.each { path, value ->
        
            // is a path?
            if (!path.startsWith('/')) return
            
            //println path
            constraint = archetype.node(path)
            if (!constraint)
            {
            parent_path = Locatable.parentPath(path)
            constraint = archetype.node(parent_path)
            
            attribute = path - (parent_path + '/') // /a[]/b[]/c - /a[]/b[]/ = c
            
            // use parent_path instead of constraint.path because the parent_path can contain an archetype ref
            // and the constraint.path is just the referenced
            if (!data_grouper[parent_path])
            {
                data_grouper[parent_path] = [:]
            }
            data_grouper[parent_path][attribute] = value // groups values from complex datatypes
            }
            else
            {
            data_grouper[path] = value
            }
        }
        
        
        
        println "data grouper"
        println data_grouper
        
        //   data_grouper.sort{ it.key }.each { path, data ->
        //     println path +" > "+ data
        //   }
        
        
        
        // =========================================================================================
        // Data validation
        
        // each item on the data grouper is a datatype to validate a gainst a constraint
        def validator
        def parent
        def errors = [:]
        data_grouper.sort{ it.key }.each { path, data ->
            
            constraint = archetype.node(path)
            //println constraint.rmTypeName // the validator will depend on the type
            
            
            // --------------------------------------------------------------------------------------
            // Get the parent object to validate using the occurrences (mandatory requires all the data
            // and optional will validate if not all the data is present)
            //
            // TODO: check for attributes of the IM
            parent = archetype.node(constraint.parent.parentNodePath())
            
            //println constraint.parent.getClass() // CSingleAttribute / CComplexAttribute
            
            // if the parent is a datavalue, it is a complex datavalue
            while (datavalues.contains(parent.rmTypeName))
            {
            parent = archetype.node(parent.parent.parentNodePath())
            }
            
            if (parent.rmTypeName != 'ELEMENT')
            {
            //println "parent NOT ELEMENT: "+ parent.rmTypeName
            println "the path is for an IM attribute: " + path
            //println parent
            return // Validation of IM attributes not supported yet
            }
            // --------------------------------------------------------------------------------------
            
            
            validator = 'validate'+ constraint.rmTypeName
            
            if ("$validator"(data, constraint, parent))
            {
            //println "valid"
            }
            else
            {
            //println "invalid"
            // path might not be the individual attribute path, but the grouper path for complex datavalues like DV_QUANTITY
            errors[path] = 'error' // we might add different types of errors here
            }
        }
        
        if (errors.size() > 0)
        {
            render view:'create_blood_pressure', model:[errors:errors]
            return
        }
        
        // ===================================================================================
        // Data binding
        
        // We create the document here for simplicity, because with archetypes
        // we should also add support for SLOTS and need to resolve the SLOTS.
        // Or use Operational Templates that have the complete document structure.
        
        def document = new Document(
            author: Clinician.get(1), // The clinician should come from a session, for that we need to add a login
            templateId: 'Encounter', 
            archetypeId: 'openEHR-EHR-COMPOSITION.encounter.v1',
            content: []
        )
        
        def s = dataBindingService.bind(data_grouper, params.archetypeId, 'content')
        println ">>> " + s
        println s as grails.converters.JSON
        
        
        // ==================================================================================
        // Data store (Grails ORM)
        
        // Try saving the document
        document.addToContent(s)
        if (!document.save()) println document.errors.allErrors
        
        println document as grails.converters.JSON
        */
        
        redirect action: 'create_blood_pressure'
    }


    private ValidationResult validateDV_QUANTITY(DvQuantity dv, constraint)
    {
        if (constraint instanceof CDvQuantity)
        {
            return constraint.isValid(dv)
        }
        else
        {
            println "DV_QTY validation not CDvQuantity"
        }

        return null
    }

    private ValidationResult validateDV_TEXT(DvText dv, constraint)
    {
        def attr_constraint = constraint.attributes.find { it.rmAttributeName == 'value' }

        if (!attr_constraint) // no constraint for the value => the value is valid
        {
            return new ValidationResult(isValid: true)
        }

        if (attr_constraint.children.size() > 0 && attr_constraint.children[0] instanceof PrimitiveObjectNode)
        {
            return attr_constraint.children[0].item.isValid(dv.value)
        }

        return new ValidationResult(isValid: true)
    }

    private ValidationResult validateDV_PROPORTION(DvProportion dv, constraint)
    {
        def attr_constraint = constraint.attributes.find { it.rmAttributeName == 'type' }

        if (!attr_constraint) // no constraint for the value => the value is valid
        {
            // FIXME: if the OPT doesn't have a constraint for the type, the OPT itself is invalid
            return new ValidationResult(isValid: true)
        }

        if (attr_constraint.children.size() > 0 && attr_constraint.children[0] instanceof PrimitiveObjectNode)
        {
            //return attr_constraint.children[0].item.isValid(dv.value)

            def proportion_kind_value = attr_constraint.children[0].item.list[0]

            switch (proportion_kind_value)
            {
                case 1: // unitary
                    if (dv.denominator != 1)
                    {
                        return new ValidationResult(isValid: false, message: "denominator '${dv.denominator}' should be 1")
                    }
                break
                case 2: // percent
                    if (dv.denominator != 100)
                    {
                        return new ValidationResult(isValid: false, message: "denominator '${dv.denominator}' should be 100")
                    }
                break
            }
        }

        return new ValidationResult(isValid: true)
    }

    

    

    /*
    private boolean validateDV_QUANTITY(data, constraint, parent)
    {
        // If there is missing data to create the datavalue,
        //   If the parent is mandatory, then the value is NOT VALID
        //   If the parent is NOT mandatory, the the value is VALID (missing data is considered as an empty value)
        def mandatory = (parent.occurrences.lower == 1)
        if (!data.units || !data.magnitude)
        {
            return !mandatory
        }
        
        def valid = false
        def validator = constraint.list.find{ it.units == data.units } // soporta multiples unidades
        try
        {
            if (validator) valid = validator.magnitude.has(data.magnitude.toDouble())
        }
        catch (NumberFormatException e)
        {
            // data entered doesnt have a valida number format
        }
        
        return valid
    }
    
    private boolean validateDV_TEXT(data, constraint, parent)
    {
        def mandatory = (parent.occurrences.lower == 1)
        if (!data)
        {
            return !mandatory
        }
        //println data
        //println constraint
        return true
    }
    
    private boolean validateCODE_PHRASE(data, constraint, parent)
    {
        def mandatory = (parent.occurrences.lower == 1)
        if (!data)
        {
            return !mandatory
        }
        
        // println data
        // println constraint
        // println constraint.codeList
        
        return constraint.codeList.contains(data) // validates a code
    }
    */
    
    
    /**
        * Sample queries based on archetypes and paths.
        */
    def query_sbp()
    {
        def systolic_bps_items = Element.findAllByArchetypeIdAndPath(
            'openEHR-EHR-OBSERVATION.blood_pressure.v1',
            '/data[at0001]/events[at0006]/data[at0003]/items[at0004]'
        )
        
        def systolic_bps = systolic_bps_items.collect{ it.value }
        
        render systolic_bps as grails.converters.JSON
    }
    
    
    def query_high_sbp()
    {
        def systolic_bps_items = Element.withCriteria() {
            eq('archetypeId', 'openEHR-EHR-OBSERVATION.blood_pressure.v1')
            eq('path', '/data[at0001]/events[at0006]/data[at0003]/items[at0004]')
            value {
            gt('magnitude', 120.0)
            }
        }
        def systolic_bps = systolic_bps_items.collect{ it.value }
        
        render systolic_bps as grails.converters.JSON
    }
}