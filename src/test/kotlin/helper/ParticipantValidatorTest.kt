package helper

import model.Participant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ParticipantValidatorTest (){

    private lateinit var testSubject: Validatable

    @Test
    fun `WHEN participant has special characters submitted THEN Exception is thrown`() {
        var  participant = Participant (null,null,false, arrayListOf("@"))
        testSubject = ParticipantValidator(listOf(participant))
        Assertions.assertThrows(Exception::class.java){
            testSubject.validate()
        }
    }

    @Test
    fun `WHEN participant has an entry of more than 5  submitted THEN Exception is thrown`() {
        var  participant = Participant (null,null,false, arrayListOf("18"))
        testSubject = ParticipantValidator(listOf(participant))
        Assertions.assertThrows(Exception::class.java){
            testSubject.validate()
        }
    }

    @Test
    fun `WHEN participant has an entry of 0 THEN Exception is thrown`() {
        var  participant = Participant (null,null,false, arrayListOf("0"))
        testSubject = ParticipantValidator(listOf(participant))
        Assertions.assertThrows(Exception::class.java){
            testSubject.validate()
        }
    }

    @Test
    fun `WHEN participant has an blank entry THEN No Exception is thrown`() {
        var  participant = Participant (null,null,false, arrayListOf(" "))
        testSubject = ParticipantValidator(listOf(participant))
        Assertions.assertDoesNotThrow((Executable { testSubject.validate() }))
    }

    @Test
    fun `WHEN participant has name in lower case and digit entry THEN No Exception is thrown`() {
        var  participant = Participant (null,null,false, arrayListOf("test","1"))
        testSubject = ParticipantValidator(listOf(participant))
        Assertions.assertDoesNotThrow((Executable { testSubject.validate() }))
    }

}