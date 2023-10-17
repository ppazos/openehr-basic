package repo

class ClinicalSession {

    Date dateCreated = new Date()
    Date dateClosed
    boolean open = true

    Patient subject
    Clinician owner

    static hasMany = [documents: Document]

    static constraints = {
    }
}
