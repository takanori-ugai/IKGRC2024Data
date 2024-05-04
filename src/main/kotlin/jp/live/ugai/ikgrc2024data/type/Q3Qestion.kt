package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class Q3Qestion(
    val id: Long,
    val senario: String,
    val question: String,
    val answers: List<Answer3>,
    val questionType: String = "multipleChoice",
    val subjects: List<String> = listOf("action"),
    val category: String = "descriptive",
)

@Serializable
data class Answer3(
    val id: Int,
    val answer: String,
    val correct: Boolean,
)
