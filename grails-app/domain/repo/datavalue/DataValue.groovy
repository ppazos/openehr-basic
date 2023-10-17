package repo.datavalue

import repo.Element
import repo.Structure
import repo.Item

class DataValue {

    static belongsTo = [Element, Structure, Item]
    
    // table per class inheritance mapping
    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    }
}
