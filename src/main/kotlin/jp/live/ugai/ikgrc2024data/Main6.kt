package jp.live.ugai.ikgrc2024data

import jp.live.ugai.ikgrc2024data.type.Answer3
import jp.live.ugai.ikgrc2024data.type.Q3Qestion
import jp.live.ugai.ikgrc2024data.type.Q5Answer
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
            val f = File("Data/Answers/q5/q5_answer_scene${scene}_Day$day.json")
            val q1Answer = format.decodeFromString<Q5Answer>(f.readText(Charsets.UTF_8))
            println(q1Answer)
            println(Date().time)
            q1Answer.answers.forEachIndexed { index, it ->
                val name = it.room
                val question =
                    Q3Qestion(
                        Date().time,
                        q1Answer.senario,
                        "Where is he at ${it.time} from the beginning?",
                        listOf(
                            Answer3(1, "livingroom", it.room == "livingroom"),
                            Answer3(2, "bathroom", it.room == "bathroom"),
                            Answer3(3, "kitchin", it.room == "kitchin"),
                            Answer3(4, "bedroom", it.room == "bedroom"),
                        ),
                        subjects = listOf("location"),
                    )
                val fout = File("Data/QA/Q5/q5_answer_scene${scene}_Day${day}_location${index}.json")
                fout.writeText(format.encodeToString(question))
                //            println(format.encodeToString(question))
            }
        }
        println("Hello, world!")
    }

    println("Hello, world!")
}

class Main6
