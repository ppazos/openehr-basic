package repo

class Ehr {

    String uid = java.util.UUID.randomUUID() as String
    Date dateCreated = new Date()

    Patient subject

    // many to one declared on the document
    // List documents
    // static hasMany = [documents: Document]

    static constraints = {
    }

    static mapping = {
        documents cascade: "all"
    }
}
