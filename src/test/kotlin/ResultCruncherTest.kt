import model.Participant
import model.Question
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResultCruncherTest (){

    private lateinit var testSubject: ResultCruncher
    @BeforeEach
    fun before(){
        //GIVEN resultcruncher is instanstiated
        testSubject = ResultCruncher
    }

    @AfterEach
    fun after(){
        testSubject.getParticipants().clear()
        testSubject.getQuestions().clear()
    }


    @Test
    fun `WHEN the same question is added twice THEN only one entry is made`() {
        var questionOne = Question("typeOne","textOne")
        var questionTwo = Question("typeTwo","textTwo")

        testSubject.addQuestion(questionOne)
        testSubject.addQuestion(questionTwo)
        assertEquals(2, testSubject.getQuestions().size)

        testSubject.addQuestion(questionTwo)
        assertEquals(2, testSubject.getQuestions().size)
    }

    @Test
    fun `WHEN the participants with different id submits the survey THEN the answers are recorded`() {
        var participantOne = Participant("a@b.c","1",true, arrayListOf("1","2","3"))
        var participantTwo = Participant("a@b.c","2",true, arrayListOf("1","2","3"))

        testSubject.addParticipant(participantOne)
        testSubject.addParticipant(participantTwo)
        assertEquals(2, testSubject.getParticipants().size)

        var participantThree = Participant("a@b.c","",true, arrayListOf("1","2","3"))
        testSubject.addParticipant(participantThree)
        assertEquals(3, testSubject.getParticipants().size)
    }

    @Test
    fun `WHEN the same participant submits the survey twice THEN the latest answers are recorded`() {
        var participantOne = Participant("a@b.c","1",true, arrayListOf("1","2","3"))
        var participantTwo = Participant("a@b.c","1",true, arrayListOf("4","5","3"))

        testSubject.addParticipant(participantOne)
        testSubject.addParticipant(participantTwo)
        assertEquals(2, testSubject.getParticipants().size)

        assertEquals(arrayListOf("1","2","3"), testSubject.getParticipants().first().answers)
    }

    @Test
    fun `WHEN questions and participant replies are processed THEN question to replies are mapped`() {
        var questionOne = Question("typeOne","textOne")
        var questionTwo = Question("typeTwo","textTwo")
        var questionThree = Question("typeThree","textThree")
        testSubject.addQuestion(questionOne)
        testSubject.addQuestion(questionTwo)
        testSubject.addQuestion(questionThree)

        var participantOne = Participant("a@b.c","1",true, arrayListOf("abc","2","xyz"))
        var participantTwo = Participant("a@b.c","2",true, arrayListOf("1","2","3"))
        var participantThree = Participant("a@b.c","3",true, arrayListOf("1","2","3"))

        testSubject.addParticipant(participantOne)
        testSubject.addParticipant(participantTwo)
        testSubject.addParticipant(participantThree)

        testSubject.beginProcessing()

        assertEquals(3,testSubject.questionToSubmittedAnswers.size)
        assertEquals(arrayListOf("abc","1","1"), testSubject.questionToSubmittedAnswers.values.first())
    }

    @Test
    fun `WHEN questions are added THEN total entries and submitted entries can be calculated `() {
        var participantOne = Participant("a@b.c","1",true, arrayListOf("abc","2","xyz"))
        var participantTwo = Participant("a@b.c","2",true, arrayListOf("1","2","3"))
        var participantThree = Participant("a@b.c","3",false, arrayListOf("1","2","3"))

        testSubject.addParticipant(participantOne)
        testSubject.addParticipant(participantTwo)
        testSubject.addParticipant(participantThree)

        assertEquals(3, testSubject.getTotalUniqueEntries())
        assertEquals(2, testSubject.getSubmittedEntries())
    }

    @Test
    fun `WHEN No questions are added THEN total entries and submitted entries can be calculated `() {

        assertEquals(0, testSubject.getTotalUniqueEntries())
        assertEquals(0, testSubject.getSubmittedEntries())
    }
}