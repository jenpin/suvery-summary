package model

import java.util.ArrayList

data class Participant (
    val email: String?,
    val id: Number?,
    val hasParticipated: Boolean,
    val answers: ArrayList<String> = arrayListOf<String>()
)