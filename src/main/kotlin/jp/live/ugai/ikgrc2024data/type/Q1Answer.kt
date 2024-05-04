package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class Q1Answer(
    val name: String,
    val senario: String,
    val answers: List<Answer0>,
)

@Serializable
data class Answer0(
    val name: String,
    val number: Int,
)
