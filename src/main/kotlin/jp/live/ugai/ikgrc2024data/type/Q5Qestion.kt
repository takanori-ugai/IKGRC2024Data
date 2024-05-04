package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class Q5Qestion(
    val id: Long,
    val senario: String,
    val question: String,
    val answers: List<Answer3>,
    val questionType: String = "multipleChoice",
    val subjects: List<String> = listOf("location"),
    val category: String = "descriptive",
)
