package repo

import repo.datavalue.DataValue

class Item {

    String type // rm_type_name
    String attr // rm_attr_name (not used)
    
    String archetypeId
    String path
    String nodeId // atNNNN

    // To simplify querying in a document
    Document parent
    
    Map attributes // attributes of the openEHR IM
    static hasMany = [attributes: DataValue]
   
    static belongsTo = [/*Document, */Structure]

    static constraints = {
        attr nullable: true
        parent nullable: true
        nodeId nullable: true
    }

    static mapping = {
        attributes cascade: "all"
    }
}
