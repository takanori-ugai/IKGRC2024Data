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
            val objs = q1Answer.answers.map { it.`object` }.toMutableList().shuffled()
            q1Answer.answers.forEachIndexed { index, it ->
                val name = it.room
                val ans = objs.mapIndexed { index, obj -> Answer3(index + 1, obj, obj == it.`object`) }

                val question =
                    Q3Qestion(
                        Date().time,
                        q1Answer.senario,
                        "What is he having at ${it.time} from the beginning?",
                        ans,
                        subjects = listOf("object"),
                    )
                val fout = File("Data/QA/Q5/q5_answer_scene${scene}_Day${day}_object${index}.json")
                fout.writeText(format.encodeToString(question))
                //            println(format.encodeToString(question))
            }
        }
        println("Hello, world!")
    }

    println("Hello, world!")
}

class Main61
