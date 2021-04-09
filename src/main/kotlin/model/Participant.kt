package model

import java.util.ArrayList

data class Participant (
    val email: String?,
    val id: String?,
    val hasParticipated: Boolean,
    val answers: MutableList<String> = arrayListOf<String>()
)