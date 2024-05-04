package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class Q5Answer(
    val name: String,
    val senario: String,
    val answers: List<Answer5>,
)

@Serializable
data class Answer5(
    val time: String,
    val room: String,
    val `object`: String,
)
