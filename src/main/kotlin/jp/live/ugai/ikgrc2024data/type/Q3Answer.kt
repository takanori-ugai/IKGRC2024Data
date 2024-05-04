package jp.live.ugai.ikgrc2024data.type

import kotlinx.serialization.Serializable

@Serializable
data class Q3Answer(
    val name: String,
    val senario: String,
    val answers: List<String>,
)
