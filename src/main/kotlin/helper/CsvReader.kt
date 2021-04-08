package helper

import ResultCruncher
import model.Participant
import model.Question
import java.io.File

class CsvReader (val resultCruncher: ResultCruncher) {

    private fun populateQuestions(lines: List<String>, header: List<String>) {
        lines.drop(1)
            .map { it.trim().split(",") }
            .map { header.zip(it).toMap() }
            .forEach {
                resultCruncher.addQuestion(Question(it.get("type")!!, it.get("text")!!))
            }
    }

    private fun populateParticpants(lines: List<String>) {
        lines.map {
            it.trim().split(",")
        }.forEach {
            println(it.subList(3, it.size))
            resultCruncher.addParticipant(
                Participant(
                    it[0],
                    it[1].toInt(),
                    !it[2].isNullOrBlank(),
                    it.subList(3, it.size).toMutableList()
                )
            )
            println(ResultCruncher.getParticipants().size)
        }
    }

    fun readFile(filename: String) {
        val contents = File(filename)
        val lines: List<String> = contents.readLines()
        if(lines.isEmpty()) throw Exception("Empty file!")

        val isHeader = listOf("@",":"," ").filter { it in lines[0] }.size == 0
        println("isHeader " + isHeader)

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
        println(arg)
        try { CsvReader(ResultCruncher).readFile(arg) }
        catch (e: Exception){
            println("Something went wrong!! "+ e.message)
        }
    }
}



