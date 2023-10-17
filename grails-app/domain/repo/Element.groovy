package repo

import repo.datavalue.DataValue
import repo.datavalue.DvText

class Element extends Item {

    DataValue value
    DvText name
    
    static belongsTo = [Document, Structure]
    
    static mapping = {
        value cascade: 'save-update'
        name cascade: 'save-update'
    }
    
    static constraints = {
        value nullable: true
        name nullable: true
    }
}
