package model

import java.math.RoundingMode
import java.text.DecimalFormat

data class SurveySummaryResult(
    private var totalParticipants: Int,
    val participantPercentage: Double,
    val questionAvg : Map<String,Double>
){

    fun printResult(){
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        println("Participation Percentage = " + df.format(participantPercentage) +"% ")
        println( "Total Participants = " + totalParticipants)
        println("Your Response Average")
        questionAvg.forEach{
            println(it.key + " : " + df.format(it.value))
        }
    }
}