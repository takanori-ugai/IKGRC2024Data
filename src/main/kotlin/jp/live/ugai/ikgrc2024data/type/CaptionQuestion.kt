package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class CaptionQestion(
    val id: Long,
    val videoId: String,
    val question: String,
    val answers: List<CaptionAnswer>,
    val questionType: String = "multipleChoice",
    val subjects: List<String> = listOf("caption"),
    val category: String = "descriptive",
)

@Serializable
data class CaptionAnswer(
    val id: Int,
    val answer: String,
    val correct: Boolean,
)
