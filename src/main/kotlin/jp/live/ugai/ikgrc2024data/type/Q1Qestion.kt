package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class Q1Qestion(
    val id: Long,
    val senario: String,
    val question: String,
    val answers: List<Answer1>,
    val questionType: String = "multipleChoice",
    val subjects: List<String> = listOf("location"),
    val category: String = "quantitative",
)

@Serializable
data class Answer1(
    val id: Int,
    val answer: Int,
    val correct: Boolean,
)
