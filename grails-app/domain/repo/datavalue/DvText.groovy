package repo.datavalue

import repo.Element

class DvText extends DataValue {

    String value
        
    static belongsTo = [Element]

    static constraints = {
    }
}
