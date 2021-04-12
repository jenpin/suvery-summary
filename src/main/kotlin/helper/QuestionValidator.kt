package helper

import model.Question


class QuestionValidator (val questions: List<Question>) : Validatable {

    private fun hasValidText(text: String) = text.isNotBlank() && ("\\s+|\\w+".toRegex(RegexOption.IGNORE_CASE)).findAll(text).count() > 0

    private fun hasValidType(type: String) = type.isNotBlank() && listOf("ratingquestion","singleselect").contains(type)

    override fun validate() {
        if(questions.isEmpty()) {
            throw Exception ("The Question file has not been provided.")
        }

        questions.forEachIndexed { index, it ->
            if (!(hasValidText(it.text) && hasValidType(it.type))) {
                throw Exception("Question at " + index + " is incorrect")
            }
        }
    }
}