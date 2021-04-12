package helper

import model.Question
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class QuestionValidatorTest() {

    private lateinit var testSubject: Validatable

    @Test
    fun `WHEN question type and text are valid THEN No Exception is thrown`() {
        var  question = Question ("ratingquestion","hello world")
        testSubject = QuestionValidator(listOf(question))
        Assertions.assertDoesNotThrow((Executable { testSubject.validate() }))

    }

    @Test
    fun `WHEN question type or text has special charactes a THEN an exception is thrown`() {
        var  question = Question ("rating&question","hello%world")
        testSubject = QuestionValidator(listOf(question))
        Assertions.assertThrows(Exception::class.java){
            testSubject.validate()
        }

    }
}