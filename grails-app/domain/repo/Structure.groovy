package repo

class Structure extends Item {

    List items
    static hasMany = [items: Item]
    
    static belongsTo = [Document, Structure]

    static constraints = {
    }

    static mapping = {
        attributes cascade: "all"
        items cascade: "all"
    }
}
