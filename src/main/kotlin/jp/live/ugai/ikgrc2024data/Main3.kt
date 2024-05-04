package jp.live.ugai.ikgrc2024data

import jp.live.ugai.ikgrc2024data.type.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.Date
import kotlin.random.Random

fun main(args: Array<String>) {
    val format =
        Json {
            encodeDefaults = true
            prettyPrint = true
//        explicitNulls = false
        }
    for (scene in 1..7) {
        for (day in 1..10) {
            val f = File("Data/Answers/q2/q2_answer_scene${scene}_Day$day.json")
            val q1Answer = format.decodeFromString<Q1Answer>(f.readText(Charsets.UTF_8))
            println(q1Answer)
            println(Date().time)
            q1Answer.answers.forEach {
                val name = it.name
                val question =
                    Q1Qestion(
                        Date().time,
                        q1Answer.senario,
                        "How many times did he $name?",
                        listOf(
                            Answer1(1, it.number, true),
                            Answer1(2, it.number + 1, false),
                        ),
                        subjects = listOf("action"),
                    )
                val fout = File("Data/QA/Q2/q2_answer_scene${scene}_Day${day}_$name.json")
                fout.writeText(format.encodeToString(question))
                //            println(format.encodeToString(question))
                val isEntered = Random.nextBoolean()
                val q2 = Q3Qestion(
                    Date().time,
                    q1Answer.senario,
                    "Did he ${name} ${if (isEntered) it.number else it.number + 1} times?",
                    listOf(
                        Answer3(1, "Yes", isEntered),
                        Answer3(2, "No", !isEntered),
                    ),
                    subjects = listOf("location"),
                    category = "descriptive",
                    questionType = "Yes/No"
                )
                val f2 = File("Data/YesNo/Q2/q2_answer_scene${scene}_Day${day}_$name.json")
                f2.writeText(format.encodeToString(q2))

            }
        }
        println("Hello, world!")
    }

    println("Hello, world!")
}

class Main3
