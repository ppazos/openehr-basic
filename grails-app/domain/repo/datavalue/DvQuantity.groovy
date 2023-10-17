package repo.datavalue

import repo.Element

class DvQuantity extends DataValue {

    BigDecimal magnitude
    String units
   
    static belongsTo = [Element]

    static constraints = {
    }
}
