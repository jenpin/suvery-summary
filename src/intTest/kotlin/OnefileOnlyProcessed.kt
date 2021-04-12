import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths

class OnefileOnlyProcessed (){

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
    fun `GIVEN only a question file is provided WHEN app is run THEN survey summary is displayed`(){
        val expected = StringBuilder("Participation Percentage = 0% ").appendln()
        expected.appendln("Total Participants = 0")
        expected.appendln("Your Response Average")
        expected.appendln("I like the kind of work I do. : 0")
        expected.appendln("\"In general : 0")
        expected.appendln("We are working at the right pace to meet our goals. : 0")
        expected.appendln("I feel empowered to get the work done for which I am responsible. : 0")
        expected.appendln("I am appropriately involved in decisions that affect my work. : 0")
        expected.appendln()

        val inputStream = Paths.get(classloader.getResource("survey-1.csv").toURI()).toString()
        helper.main(arrayOf<String>(inputStream))

        Assertions.assertEquals(expected.toString().trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    fun `GIVEN only the answer file is provided WHEN app is run THEN survey summary is displayed`(){
        val expected = StringBuilder("Participation Percentage = 83.34% ").appendln()
        expected.appendln("Total Participants = 6")
        expected.appendln("Your Response Average")
        expected.appendln()

        val inputStream = Paths.get(classloader.getResource("survey-1-responses.csv").toURI()).toString()
        helper.main(arrayOf<String>(inputStream))

        Assertions.assertEquals(expected.toString().trim(), outputStreamCaptor.toString().trim());
    }
}