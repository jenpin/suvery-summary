import model.Participant
import model.Question

object ResultCruncher {

    private val questions: ArrayList<Question> = arrayListOf<Question>()
    private val participants: ArrayList<Participant> = arrayListOf<Participant>()
    val questionToAnswer: MutableMap<Question,List<Int>> = mutableMapOf()

    private fun calculateParticipationPercentage(){}

    private fun populateQuestionAnswerMap(){}

    fun addQuestion(value: Question) = questions.add(value)
    fun addParticipant(value: Participant) = participants.add(value)

    fun getQuestions () : ArrayList<Question> = questions
    fun getParticipants () :  ArrayList<Participant> = participants
}