import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class Emptyfiles {
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
    fun `GIVEN question or response are empty WHEN app is run THEN survey summary is displayed`(){

        val questions = Paths.get(classloader.getResource("empty-questions.csv").toURI()).toString()
        val answers = Paths.get(classloader.getResource("empty-answers.csv").toURI()).toString()
        val inputStream = arrayOf(questions,answers)

        val execption = Assertions.assertThrows(Exception::class.java){
            helper.main(inputStream)
        }
        Assertions.assertEquals( "The Question file has not been provided.", execption.message);
    }

}
