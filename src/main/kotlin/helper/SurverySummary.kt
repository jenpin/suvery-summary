package helper

import ResultCruncher
import model.Question
import model.SurveySummaryResult


class SurverySummary(val resultCruncher: ResultCruncher){
    val questionAvg : MutableMap<String,Double> = mutableMapOf()

    private fun isRatingOnlyQuestion(question : Question) = question.type.equals("ratingquestion")

    private fun populateSurveySummaryResult(totalEntries: Int) = SurveySummaryResult(
        totalEntries,
        calculateParticipationPercentage(resultCruncher.getSubmittedEntries().toDouble(),totalEntries.toDouble()),
        questionAvg).printResult()

     fun calculateParticipationPercentage(submittedEntries: Double, totalEntries: Double): Double {
         if(totalEntries != 0.0){
             val value = submittedEntries / totalEntries
             return value * 100
         }
       return 0.0
    }

    fun calculateAvgRatingPerQuestion(questionToAnswers: Map<Question,List<String>>){
        questionToAnswers.entries.forEach {
            if(isRatingOnlyQuestion(it.key)){
                val avg  =  if(it.value.size > 0 ) it.value.map{it.toInt()}.average() else 0.0
                questionAvg.put(it.key.text,avg)
            }
        }
    }

    fun displayResult(){
        calculateAvgRatingPerQuestion(resultCruncher.questionToSubmittedAnswers)
        populateSurveySummaryResult(resultCruncher.getTotalUniqueEntries())
    }
}