package repo.datavalue

import repo.Element

class DvCodedText extends DvText {

    String codeString
    String terminologyIdName
    String terminologyIdVersion
    
    static belongsTo = [Element]

    static constraints = {
    }
}
