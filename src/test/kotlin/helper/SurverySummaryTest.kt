package helper

import ResultCruncher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class SurverySummaryTest (){
    
    private lateinit var testSubject: SurverySummary
    private val mockResultCrucher = Mockito.mock(ResultCruncher::class.java)
    
    @BeforeEach
    fun before(){
        testSubject = SurverySummary(mockResultCrucher)
    }

}