package helper

import ResultCruncher
import model.Question
import model.SurveySummaryResult
import java.text.DecimalFormat


class SurverySummary(val resultCruncher: ResultCruncher){

    private fun isRatingOnlyQuestion(question : Question) = question.type.equals("ratingquestion")
    val questionAvg : MutableMap<String,Double> = mutableMapOf()


    private fun calculateParticipationPercentage(): Double {
        val diff =  resultCruncher.getSubmittedEntries()
        val value = diff / resultCruncher.getTotalUniqueEntries().toDouble()
        return value * 100.toDouble()
    }


    private fun calculateAvgRatingPerQuestion(){
        val questionToAnswers = resultCruncher.questionToSubmittedAnswers
        questionToAnswers.keys.forEach {

            if(isRatingOnlyQuestion(it)){
                val avg  =  if(questionToAnswers.get(it)?.size!! > 0 ) {
                    questionToAnswers.get(it)?.map{it.toInt()}?.average()
                }
                 else 0.0
                questionAvg.put(it.text,avg!!)
            }
        }
    }

    private fun populateSurveySummaryResult(){
        val result = SurveySummaryResult(resultCruncher.getTotalUniqueEntries(),calculateParticipationPercentage(),questionAvg)
        result.printResult()
    }

    fun displayResult(){
        calculateAvgRatingPerQuestion()
        populateSurveySummaryResult()
    }
}