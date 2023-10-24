package repo.datavalue

import repo.Element

class DvProportion extends DataValue {

    BigDecimal numerator
    BigDecimal denominator
   
    static belongsTo = [Element]

    static constraints = {
    }
}
