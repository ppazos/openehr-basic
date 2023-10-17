package repo

class Document {

    Date start = new Date()
    Date end
    String category = 'event' // event | persistent | episodic
    String templateId
    String archetypeId
    String versionUid

    Clinician author

    Ehr ehr

    // many to one declared on the Item
    // List content
    // static hasMany = [content: Item]

    // Data from the UI
    // Map<String, String>
    Map bindData

    static belongsTo = [Ehr]

    static constraints = {
        end nullable: true
        versionUid nullable: true
    }
    
    static mapping = {
        content cascade: 'save-update' // save the document to save all the structure in cascade
    }

    def beforeInsert() {

        bindData = bindData?.collectEntries { key, value ->
            [key, value.toString()] // need the values to be string
        }
   }
}
