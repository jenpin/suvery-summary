package helper

import ResultCruncher
import model.Participant
import model.Question
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SurverySummaryTest (){
    private lateinit var testSubject: SurverySummary
    private lateinit var result: ResultCruncher

    @BeforeEach
    fun before(){
        result = ResultCruncher
        testSubject = SurverySummary(result)
    }

    @AfterEach
    fun after(){
        result.questionToSubmittedAnswers.clear()
        result.getQuestions().clear()
        result.getParticipants().clear()
    }

    @Test
    fun `WHEN displayResult is invoked then question average is created`(){
        var questionOne = Question("ratingquestion","textOne")
        var questionTwo = Question("ratingquestion","textTwo")
        var participantOne = Participant("a@b.c","1",true, arrayListOf("1","2","3"))
        var participantTwo = Participant("a@b.c","2",true, arrayListOf("1","2","3"))

        result.addQuestion(listOf(questionOne, questionTwo))
        result.addParticipant(listOf(participantOne, participantTwo))
        result.beginProcessing()

        testSubject.calculateAvgRatingPerQuestion(result.questionToSubmittedAnswers)

        assertEquals(2, testSubject.questionAvg.size)
    }

    @Test
    fun `WHEN no questions are given THEN the map is empty`(){
        testSubject.calculateAvgRatingPerQuestion(emptyMap())
        assertEquals(0, testSubject.questionAvg.size)
    }

    @Test
    fun `GIVEN no submitted entry WHEN calculateparticipation percentage is invoked THEN the percentage is returned `(){
        assertEquals(0.0, testSubject.calculateParticipationPercentage(0.0,1.0))
    }

    @Test
    fun `GIVEN no entries WHEN calculateparticipation percentage is invoked THEN the percentage is returned `(){
        assertEquals(0.0, testSubject.calculateParticipationPercentage(0.0,0.0))
    }

    @Test
    fun `GIVEN valid entries WHEN calculateparticipation percentage is invoked THEN the percentage is returned `(){
        assertEquals(20.0, testSubject.calculateParticipationPercentage(2.0,10.0))
    }

}