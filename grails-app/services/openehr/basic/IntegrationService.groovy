package openehr.basic

import grails.gorm.transactions.Transactional
import com.cabolabs.openehr.rm_1_0_2.composition.*
import com.cabolabs.openehr.rm_1_0_2.composition.content.entry.*
import com.cabolabs.openehr.rm_1_0_2.data_structures.item_structure.*
import com.cabolabs.openehr.rm_1_0_2.data_structures.item_structure.representation.*
import com.cabolabs.openehr.rm_1_0_2.data_types.quantity.date_time.*
import com.cabolabs.openehr.rm_1_0_2.data_types.text.*
import com.cabolabs.openehr.rm_1_0_2.support.identification.*
import com.cabolabs.openehr.rm_1_0_2.common.generic.*
import com.cabolabs.openehr.rm_1_0_2.common.archetyped.*
import com.cabolabs.openehr.rm_1_0_2.data_structures.history.*

import com.cabolabs.openehr.opt.manager.OptManager
import com.cabolabs.openehr.opt.manager.OptRepository
import com.cabolabs.openehr.opt.manager.OptRepositoryFSImpl

import com.cabolabs.openehr.formats.OpenEhrJsonSerializer
import com.cabolabs.openehr.opt.model.*

import repo.*
import repo.datavalue.*

@Transactional
class IntegrationService {

    // transforms a local document into an openEHR composition
    Composition buildComposition(Document doc)
    {
        def opt = getTemplate(doc.templateId)
        if (!opt)
        {
            throw new Exception("opt $template_id not found")
        }

        Composition c = new Composition(
			name: new DvText(
				value: opt.concept
			),
			language: new CodePhrase(
				terminology_id: new TerminologyId(
					value: "ISO_639-1"
				),
				code_string: "es"
			),
			territory: new CodePhrase(
				terminology_id: new TerminologyId(
					value: "ISO_3166-1"
				),
				code_string: "UY"
			),
			archetype_details: new Archetyped(
				archetype_id: new ArchetypeId(
					value: doc.archetypeId
				),
				template_id: new TemplateId(
					value: doc.templateId
				),
				rm_version: "1.0.2"
			),
			archetype_node_id: doc.archetypeId,
			category: new DvCodedText(
				value: "event",
				defining_code: new CodePhrase(
					terminology_id: new TerminologyId(
						value: "openehr"
					),
					code_string: "433"
				)
			),
			composer: new PartyIdentified(
				name: doc.author.name,
				external_ref: new PartyRef(
					namespace: "DEMOGRAPHIC",
					type: "PERSON",
					id: new HierObjectId(
						value: doc.author.uid
					)
				)
			),
            context: new EventContext(
				start_time: new DvDateTime(
					value: dateTimeInUTC(doc.start)
				),
				setting: new DvCodedText(
					value: 'home',
					defining_code: new CodePhrase(
						terminology_id: new TerminologyId(
							value: "openehr"
						),
						code_string: "225"
					)
				)
			),
            content: []
        )

        // need to have the direct children of compo which will always be structure
        def items = Structure.findAllByParentAndTop(doc, true)
        def method
        def rm_content_item
        items.each { content_item ->

            method = 'build'+content_item.type // e.g. buildOBSERVATION
            rm_content_item = this."$method"(content_item, opt)
            c.content << rm_content_item
        }

        return c
    }

    private Observation buildOBSERVATION(Structure struct, opt)
    {
        println 'buildOBS '+ opt.getTerm(struct.archetypeId, struct.nodeId)

        def obs = new Observation()
        fillENTRY(struct, obs, opt)

        def data = struct.items.find{ it.attr == 'data'}
        def rm_data = this.buildHISTORY(data, opt)
        obs.data = rm_data

        return obs
    }

    private History buildHISTORY(Structure struct, opt)
    {
        println 'buildHISTORY '+ opt.getTerm(struct.archetypeId, struct.nodeId)

        def hist = new History(
            origin: new DvDateTime(
                value: dateTimeInUTC(struct.parent.start)
            ),
            events: []
        )

        fillLOCATABLE(struct, hist, opt)

        def events = struct.items.findAll{ it.attr == 'events'}
        def method, rm_event
        events.each { event ->
            method = 'build'+ event.type
            rm_event = this."$method"(event, opt)
            hist.events << rm_event
        }

        // TODO: map state

        return hist
    }


    private buildEVENT(Structure struct, opt)
    {
        buildPOINT_EVENT(struct, opt)
    }

    private buildPOINT_EVENT(Structure struct, opt)
    {
        def event = new PointEvent(
            time: new DvDateTime(
                value: dateTimeInUTC(struct.parent.start)
            )
        )

        fillLOCATABLE(struct, event, opt)

        def data = struct.items.find{ it.attr == 'data'}
        def method ='build'+ data.type
        def rm_data = this."$method"(data, opt)
        event.data = rm_data

        return event
    }

    private buildITEM_TREE(Structure struct, opt)
    {
        def tree = new ItemTree(
            items: []
        )

        fillLOCATABLE(struct, tree, opt)

        def items = struct.items.findAll{ it.attr == 'items'}
        def method, rm_item
        items.each { item ->
            method = 'build'+ item.type
            rm_item = this."$method"(item, opt)
            tree.items << rm_item
        }

        return tree
    }

    private buildCLUSTER(Structure struct, opt)
    {
        def cluster = new Cluster(
            items: []
        )

        fillLOCATABLE(struct, cluster, opt)

        def items = struct.items.findAll{ it.attr == 'items'}
        def method, rm_item
        items.each { item ->
            method = 'build'+ item.type
            rm_item = this."$method"(item, opt)
            cluster.items << rm_item
        }
         
        return cluster
    }

    private buildELEMENT(repo.Element p_elem, opt)
    {
        def elem = new com.cabolabs.openehr.rm_1_0_2.data_structures.item_structure.representation.Element()

        fillLOCATABLE(p_elem, elem, opt)

        // TODO: bind value

        def method = 'build'+ p_elem.value.class.simpleName
        def rm_value = this."$method"(p_elem.value)

        elem.value = rm_value

        return elem
    }


    private buildDvQuantity(repo.datavalue.DvQuantity value)
    {
        def dv = new com.cabolabs.openehr.rm_1_0_2.data_types.quantity.DvQuantity(
            magnitude: value.magnitude.doubleValue(),
            units: value.units
        )

        return dv
    }

    private buildDvProportion(repo.datavalue.DvProportion value)
    {
        // FIXME: missing required type, need to check the opt!
        def dv = new com.cabolabs.openehr.rm_1_0_2.data_types.quantity.DvProportion(
            numerator: value.numerator.floatValue(),
            denominator: value.denominator.floatValue()
            // type: ?????
        )

        return dv
    }

    private buildDvText(repo.datavalue.DvText value)
    {
        def dv = new com.cabolabs.openehr.rm_1_0_2.data_types.text.DvText(
            value: value.value
        )

        return dv
    }

    private void fillENTRY(item, entry, opt)
    {
        entry.language = new CodePhrase(
            terminology_id: new TerminologyId(
                value: "ISO_639-1"
            ),
            code_string: "es"
        )

        entry.encoding = new CodePhrase(
            terminology_id: new TerminologyId(
                value: "Unicode"
            ),
            code_string: "UTF-8"
        )

        entry.subject = new PartySelf()

        fillLOCATABLE(item, entry, opt)
    }

    private void fillLOCATABLE(item, locatable, opt)
    {
        locatable.name = new DvText(
            value: opt.getTerm(item.archetypeId, item.nodeId)
        )

        // add archetype details only for the archetype root nodes
        if (item.archetypeRoot)
        {
            locatable.archetype_details = new Archetyped(
                archetype_id: new ArchetypeId(
                    value: item.archetypeId
                ),
                template_id: new TemplateId(
                    value: item.parent.templateId
                ),
                rm_version: "1.0.2"
            )
        }

        locatable.archetype_node_id = item.nodeId
    }

    private String dateTimeInUTC(Date date)
    {
        return date.format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone('UTC'))
    }

    OperationalTemplate getTemplate(String templateId)
    {
        String opt_repo_path = '.'
        OptRepository repo = new OptRepositoryFSImpl(opt_repo_path)

        OptManager opt_manager = OptManager.getInstance()
        opt_manager.init(repo)

        String namespace = 'opts'

        return opt_manager.getOpt(templateId, namespace) // can be null!
    }

    // RM composition to JSON transformation
    String serializeComposition(Composition c)
	{
		def serializer = new OpenEhrJsonSerializer()
		serializer.serialize(c)
	}
}
