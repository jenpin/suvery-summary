package model

data class SurveySummary(val totalParticipants: Int = 0){
    val participantPercentage: Double = 0.0
    val questionAvg : Map<String,Array<Int>> = emptyMap()
}