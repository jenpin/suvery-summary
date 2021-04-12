package helper

import ResultCruncher
import model.Participant
import model.Question
import java.io.File

class CsvReader (val resultCruncher: ResultCruncher) {

    private fun populateQuestions(lines: List<String>, header: List<String>) {
        var questions = mutableListOf<Question>()

        lines.drop(1)
            .map { it.trim().split(",") }
            .map { header.zip(it).toMap() }
            .forEach {
                questions.add(Question(it.get("type")!!,it.get("text")!!))
            }
        QuestionValidator(questions).validate()
        resultCruncher.addQuestion(questions)
    }

    private fun populateParticpants(lines: List<String>) {
        var participants = mutableListOf<Participant>()

        lines.map {
            it.trim().split(",")
        }.forEach {
            participants.add(Participant(
                    it[0],
                    it[1],
                    !it[2].isNullOrBlank(),
                    it.subList(3, it.size).toMutableList()
                ))
        }
        ParticipantValidator(participants).validate()
        resultCruncher.addParticipant(participants)
    }

    fun readFile(filename: String) {
        val contents = File(filename)
        val lines: List<String> = contents.readLines()
        if(lines.isEmpty()) throw Exception("Empty file!")

        val isHeader = listOf("@",":"," ").filter { it in lines[0] }.size == 0

        if (isHeader) {
            val header = lines[0].trim().split(",")
            populateQuestions(lines, header)
        } else {
            populateParticpants(lines)
        }
    }
}

fun main(args: Array<String>) {
    for (arg in args) {
            CsvReader(ResultCruncher).readFile(arg)
    }
    ResultCruncher.beginProcessing()
}



