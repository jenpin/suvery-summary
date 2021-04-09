import helper.SurverySummary
import model.Participant
import model.Question
import java.util.LinkedHashSet

object ResultCruncher {

    private val questions: LinkedHashSet<Question> = linkedSetOf<Question>()
    private val participants: LinkedHashSet<Participant> = linkedSetOf<Participant>()
    val questionToSubmittedAnswers: MutableMap<Question, MutableList<String>> = mutableMapOf()

    private fun populateQuestionAnswerMap(){
        questions.forEach {
            questionToSubmittedAnswers.put(it, mutableListOf<String>())
        }

        participants.forEach {
            val answers = it.answers
            if(answers.size == questionToSubmittedAnswers.keys.size && it.hasParticipated){
                questions.forEachIndexed { index, question ->
                    val rearrangedAnswer = questionToSubmittedAnswers.getValue(question)
                    rearrangedAnswer.add(answers[index])
                }
            }

        }
        println(questionToSubmittedAnswers.values)
    }

    fun addQuestion(value: Question) = questions.add(value)
    fun addParticipant(value: Participant) = participants.add(value)

    fun getQuestions () : LinkedHashSet<Question> = questions
    fun getParticipants () :  LinkedHashSet<Participant> = participants

    fun getTotalUniqueEntries() = participants.size
    fun getSubmittedEntries() = participants.filter { it.hasParticipated }.size

    fun beginProcessing() {
        val summary: SurverySummary = SurverySummary(this)
        populateQuestionAnswerMap()
        summary.displayResult()
    }
}