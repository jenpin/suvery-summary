package helper

import ResultCruncher
import model.Participant
import model.Question
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.nio.file.Paths


class CsvReaderTest {

    private lateinit var testSubject: CsvReader
    private lateinit var classloader: ClassLoader
    val mockResult = Mockito.mock(ResultCruncher::class.java)

    @BeforeEach
    fun before() {
        classloader = Thread.currentThread().contextClassLoader
        testSubject = CsvReader(mockResult)
    }

    @Test
    fun `GIVEN for a file with header WHEN the readfile is invoked THEN question models are created`() {

        val mockQuestion = Question("a", "I like the kind of work I do.")
        `when`(mockResult.addQuestion(mockQuestion)).thenReturn(true)

        val filename = Paths.get(classloader.getResource("file-with-header.csv").toURI()).toString()
        testSubject.readFile(filename)
        verify(mockResult).addQuestion(mockQuestion)
    }

    @Test
    fun `GIVEN for a filename without header WHEN the readfile is invoked THEN the participant models are created`() {

        val mockParticipant = Participant("employee1@abc.xyz", "1",true, arrayListOf("5","5","5"))
        `when`(mockResult.addParticipant(mockParticipant)).thenReturn(true)

        val filename = Paths.get(classloader.getResource("file-without-header.csv").toURI()).toString()
        testSubject.readFile(filename)

        verify(mockResult).addParticipant(mockParticipant)
    }


    @Test
    fun `GIVEN for a file with no contents WHEN the readfile is invoked THEN the participant models are created`() {
        val filename = Paths.get(classloader.getResource("blank-file.csv").toURI()).toString()

        val exception = Assertions.assertThrows(Exception::class.java){
            testSubject.readFile(filename)
        }
        Assertions.assertEquals("Empty file!", exception.message)
    }

    @Test
    fun `GIVEN for a file with swapped headers WHEN the readfile is invoked THEN question models are created`() {
        val psuedoQuestion = Question("a", "I like")
        `when`(mockResult.addQuestion(psuedoQuestion)).thenReturn(true)

        val filename = Paths.get(classloader.getResource("swapped-headers.csv").toURI()).toString()
        testSubject.readFile(filename)
        verify(mockResult).addQuestion(psuedoQuestion)
    }

    @Test
    fun `GIVEN for gibberish file content WHEN the readfile is invoked THEN participants models are created`() {
        val filename = Paths.get(classloader.getResource("invalid-content.csv").toURI()).toString()
        Assertions.assertThrows(Exception::class.java){
            testSubject.readFile(filename)
        }
    }
}