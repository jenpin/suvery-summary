import helper.CsvReader
import model.SurveySummaryResult
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths

class SuccessScenarioCase3(){

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
        val expected = StringBuilder("Participation Percentage = 0% ").appendln()
        expected.appendln("Total Participants = 5")
        expected.appendln("Your Response Average")
        expected.appendln("I like the kind of work I do. : 0")
        expected.appendln("I have the resource I need to be effective. : 0")
        expected.appendln("I feel empowered to get the work done for which I am responsible. : 0")
        expected.appendln()

        val inputStream = Paths.get(classloader.getResource("survey-3.csv").toURI()).toString()
        val responseFile = Paths.get(classloader.getResource("survey-3-responses.csv").toURI()).toString()
        helper.main(arrayOf<String>(inputStream,responseFile))

        assertEquals(expected.toString().trim(), outputStreamCaptor.toString().trim());
    }
}