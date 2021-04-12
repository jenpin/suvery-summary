import helper.CsvReader
import model.SurveySummaryResult
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths

class SuccessScenarioCase2(){

    private val standardOut = System.out
    private val standardError = System.err
    private lateinit var classloader: ClassLoader

    private val outputStreamCaptor = ByteArrayOutputStream()
    private val errorStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun before(){
        classloader = Thread.currentThread().contextClassLoader
        System.setOut(PrintStream(outputStreamCaptor))
        System.setErr(PrintStream(errorStreamCaptor))
    }

    @AfterEach
    fun after() {
        System.setOut(standardOut)
        System.setErr(standardError)
        ResultCruncher.questionToSubmittedAnswers.clear()
        ResultCruncher.getQuestions().clear()
        ResultCruncher.getParticipants().clear()
    }

    @Test
    fun `GIVEN question and response files are provided WHEN app is run THEN survey summary is displayed`(){
        val expected = StringBuilder("Participation Percentage = 100% ").appendln()
        expected.appendln("Total Participants = 5")
        expected.appendln("Your Response Average")
        expected.appendln("I like the kind of work I do. : 4.6")
        expected.appendln("\"In general : 5")
        expected.appendln("We are working at the right pace to meet our goals. : 5")
        expected.appendln("I feel empowered to get the work done for which I am responsible. : 3.6")
        expected.appendln()

        val inputStream = Paths.get(classloader.getResource("survey-2.csv").toURI()).toString()
        val responseFile = Paths.get(classloader.getResource("survey-2-responses.csv").toURI()).toString()
        helper.main(arrayOf<String>(inputStream,responseFile))

        assertEquals(expected.toString().trim(), outputStreamCaptor.toString().trim());
    }
}