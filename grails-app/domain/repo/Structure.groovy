package repo

class Structure extends Item {

    List items
    static hasMany = [items: Item]

    // top = true means the item is a direct child of document
    boolean top
    
    static belongsTo = [Document, Structure]

    static constraints = {
    }

    static mapping = {
        attributes cascade: "all"
        items cascade: "all"
    }
}
