package repo

class Patient {

    String uid = java.util.UUID.randomUUID() as String
    String name

    static constraints = {
    }
}
