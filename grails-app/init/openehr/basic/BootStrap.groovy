package openehr.basic

import repo.*
import repo.datavalue.*
import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->

        if (Ehr.count() == 0)
        {
            setup()
        }
    }
    def destroy = {
    }

    @Transactional
    def setup()
    {
        def clinicians = [
            new Clinician(
                name: 'Gregory House'
            ),
            new Clinician(
                name: 'Michael J. Fox'
            )
        ]

        clinicians.each { clinician ->
            clinician.save(failOnError: true)
        }


        def patients = [
            new Patient(
                name: 'Pablo Pazos'
            ),
            new Patient(
                name: 'Salvador Dalí'
            )
        ]

        patients.each { patient ->
            patient.save(failOnError: true)
        }

        def ehrs = [
            new Ehr(
                subject: patients[0]
            ),
            new Ehr(
                subject: patients[1]
            )
        ]

        ehrs.each { ehr ->
            ehr.save(failOnError: true)
        }

        /*
        def ehr_documents = [
            [ // docs for ehr 0
                new Document(
                    author: clinicians[0],
                    templateId: 'Monitoreo de signos vitales',
                    archetypeId: 'openEHR-EHR-COMPOSITION.monitoreo_de_signos_vitales.v1'
                ),
                new Document(
                    author: clinicians[1],
                    templateId: 'Monitoreo de signos vitales',
                    archetypeId: 'openEHR-EHR-COMPOSITION.monitoreo_de_signos_vitales.v1'
                )
                // new Document(
                //     author: clinicians[1],
                //     templateId: 'Consulta',
                //     archetypeId: 'openEHR-EHR-COMPOSITION.consulta.v1'
                // )
                
            ],
            [ // docs for ehr 1
                new Document(
                    author: clinicians[0],
                    templateId: 'Monitoreo de signos vitales',
                    archetypeId: 'openEHR-EHR-COMPOSITION.monitoreo_de_signos_vitales.v1'
                ),
                new Document(
                    author: clinicians[1],
                    templateId: 'Monitoreo de signos vitales',
                    archetypeId: 'openEHR-EHR-COMPOSITION.monitoreo_de_signos_vitales.v1'
                )
                // new Document(
                //     author: clinicians[1],
                //     templateId: 'Consulta',
                //     archetypeId: 'openEHR-EHR-COMPOSITION.consulta.v1'
                // )
            ]
        ]

        def contents = [
            [ // contents for docs of ehr 0
                new Structure(
                    type: 'POINT_EVENT',
                    // path from the template root to this node
                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]',
                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                    nodeId: 'at0002',
                    attributes: [
                        time: new DvDateTime(
                            value: new Date()
                        )
                    ],
                    // /data[at0003] => ITEM_TREE
                    items: [
                        new Structure(
                            type: 'ITEM_TREE',
                            // path from the template root to this node
                            path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]',
                            archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                            nodeId: 'at0003',
                            // /items[at0004] => ELEMENT
                            items: [
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0004',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 120,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Sistolica'
                                    )
                                ),
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0005',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 91,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Diastolica'
                                    )
                                )
                            ]
                        )
                    ]
                ),

                new Structure(
                    type: 'POINT_EVENT',
                    // path from the template root to this node
                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]',
                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                    nodeId: 'at0002',
                    attributes: [
                        time: new DvDateTime(
                            value: new Date()
                        )
                    ],
                    // /data[at0003] => ITEM_TREE
                    items: [
                        new Structure(
                            type: 'ITEM_TREE',
                            // path from the template root to this node
                            path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]',
                            archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                            nodeId: 'at0003',
                            // /items[at0004] => ELEMENT
                            items: [
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0004',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 135,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Sistolica'
                                    )
                                ),
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0005',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 94,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Diastolica'
                                    )
                                )
                            ]
                        )
                    ]
                )
            ],
            [ // contents for docs of ehr 1
                new Structure(
                    type: 'POINT_EVENT',
                    // path from the template root to this node
                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]',
                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                    nodeId: 'at0002',
                    attributes: [
                        time: new DvDateTime(
                            value: new Date()
                        )
                    ],
                    // /data[at0003] => ITEM_TREE
                    items: [
                        new Structure(
                            type: 'ITEM_TREE',
                            // path from the template root to this node
                            path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]',
                            archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                            nodeId: 'at0003',
                            // /items[at0004] => ELEMENT
                            items: [
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0004',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 139,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Sistolica'
                                    )
                                ),
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0005',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 97,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Diastolica'
                                    )
                                )
                            ]
                        )
                    ]
                ),

                new Structure(
                    type: 'POINT_EVENT',
                    // path from the template root to this node
                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]',
                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                    nodeId: 'at0002',
                    attributes: [
                        time: new DvDateTime(
                            value: new Date()
                        )
                    ],
                    // /data[at0003] => ITEM_TREE
                    items: [
                        new Structure(
                            type: 'ITEM_TREE',
                            // path from the template root to this node
                            path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]',
                            archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                            nodeId: 'at0003',
                            // /items[at0004] => ELEMENT
                            items: [
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0004',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 110,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Sistolica'
                                    )
                                ),
                                new Element(
                                    type: 'ELEMENT',
                                    path: '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]',
                                    archetypeId: 'openEHR-EHR-OBSERVATION.presion_arterial.v1',
                                    nodeId: 'at0005',
                                    // /value => DV_QUANTITY
                                    value: new DvQuantity(
                                        magnitude: 76,
                                        units: 'mm[Hg]'
                                    ),
                                    // We could get the name from the OPT
                                    name: new DvText(
                                        value: 'Diastolica'
                                    )
                                )
                            ]
                        )
                    ]
                )
            ]
        ]

        ehr_documents.eachWithIndex { documents, ehr_idx ->

            documents.eachWithIndex { document, doc_idx ->
            
                document.ehr = ehrs[ehr_idx]
                document.save(failOnError: true)

                set_document_recursive(document, contents[ehr_idx][doc_idx])
                contents[ehr_idx][doc_idx].save(failOnError: true)
            }
        }

        */
    }

    def set_document_recursive(Document parent, Structure item)
    {
        item.parent = parent
        item.items.each { set_document_recursive(parent, it) }
    }

    def set_document_recursive(Document parent, Element item)
    {
        item.parent = parent
    }
}
