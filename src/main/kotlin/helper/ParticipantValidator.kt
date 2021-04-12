package helper

import model.Participant

interface Validatable {
    fun validate()
}

class ParticipantValidator (val participants: List<Participant>) : Validatable {

    private fun hasValidResponse(replies: List<String>) = replies.all { it.matches("\\s*|[a-z1-5]+".toRegex(RegexOption.IGNORE_CASE)) }

    override fun validate(){
        if(participants.isEmpty()) {
            throw Exception ("The Participants file has not been provided ")
        }

        participants.forEachIndexed { index, it ->
            if ( ! hasValidResponse(it.answers)) {
                throw Exception("Participant at " + index + " has an incorrect entry")
            }
        }
    }
}
