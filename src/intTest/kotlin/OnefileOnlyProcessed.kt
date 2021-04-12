import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.PrintStream
import java.nio.file.Paths

class OnefileOnlyProcessed (){

    private lateinit var classloader: ClassLoader
    @BeforeEach
    fun before(){
        classloader = Thread.currentThread().contextClassLoader
    }

    @AfterEach
    fun after() {
        ResultCruncher.questionToSubmittedAnswers.clear()
        ResultCruncher.getQuestions().clear()
        ResultCruncher.getParticipants().clear()
    }

    @Test
    fun `GIVEN question and response files are provided WHEN app is run THEN survey summary is displayed`(){
        val expected = StringBuilder("Participation Percentage = 83.34% ").appendln()
        expected.appendln("Total Participants = 6")
        expected.appendln("Your Response Average")
        expected.appendln("I like the kind of work I do. : 4.6")
        expected.appendln("\"In general : 5")
        expected.appendln("We are working at the right pace to meet our goals. : 5")
        expected.appendln("I feel empowered to get the work done for which I am responsible. : 3.6")
        expected.appendln("I am appropriately involved in decisions that affect my work. : 3.6")
        expected.appendln()

        val inputStream = Paths.get(classloader.getResource("survey-1.csv").toURI()).toString()


        val execption = Assertions.assertThrows(Exception::class.java){
            helper.main(arrayOf<String>(inputStream))
        }
        Assertions.assertEquals( "The Participants file has not been provided.", execption.message);
    }
}