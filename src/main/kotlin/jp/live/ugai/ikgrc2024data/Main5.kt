package jp.live.ugai.ikgrc2024data

import jp.live.ugai.ikgrc2024data.type.Answer3
import jp.live.ugai.ikgrc2024data.type.Q3Answer
import jp.live.ugai.ikgrc2024data.type.Q3Qestion
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.Date

fun main(args: Array<String>) {
    val format =
        Json {
            encodeDefaults = true
            prettyPrint = true
//        explicitNulls = false
        }
    for (scene in 1..7) {
        for (day in 1..10) {
            val f = File("Data/Answers/q4/q4_answer_scene${scene}_Day$day.json")
            val q1Answer = format.decodeFromString<Q3Answer>(f.readText(Charsets.UTF_8))
            println(q1Answer)
            println(Date().time)
            val name = q1Answer.answers[0]
            val question =
                Q3Qestion(
                    Date().time,
                    q1Answer.senario,
                    "What did he do just before he first entered the kitchen?",
                    listOf(
                        Answer3(1, name, true),
                        Answer3(2, "READ", false),
                    ),
                    subjects = listOf("action"),
                )
            val fout = File("Data/QA/Q4/q4_answer_scene${scene}_Day$day.json")
            fout.writeText(format.encodeToString(question))
            //            println(format.encodeToString(question))
        }
        println("Hello, world!")
    }

    println("Hello, world!")
}

class Main5
