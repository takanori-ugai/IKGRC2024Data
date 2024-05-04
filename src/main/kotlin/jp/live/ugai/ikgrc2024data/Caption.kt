package jp.live.ugai.ikgrc2024data

import jp.live.ugai.ikgrc2024data.type.*
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
    val cnames = mutableListOf<String>()
    val names = mutableListOf<String>()
    val f = File("Data/Program")
    f.walkTopDown().forEach {
        if (it.name.endsWith(".txt")) {
            var name = it.path.replace("Data\\Program\\", "")
            name = name.replace("\\program", "")
            name = name.replaceFirst("\\", "/")
            name = name.replace("\\", "/movies/")
            name = name.replace(".txt", "")
            cnames.add(name)
            var name2 = it.name.replace(Regex("[0-9]*\\.txt"), "")
            if(!names.contains(name2)) names.add(name2)
            println(name)
        }
    }
    println(names)
    cnames.forEach { it ->
        val ans = mutableListOf<CaptionAnswer>()
        names.forEachIndexed() { index, it2 ->
            ans.add(CaptionAnswer(
                index,
                it2.replace("_"," "),
                it.contains(it2)
            ))
        }

        val canswer = CaptionQestion(
            Date().time,
            it,
            "Which is the most appropriate caption of the movie?",
            ans,
            subjects = listOf("caption"),
        )
        val f = File("Data/QA/Caption/${it.replace("/","_")}.json")
        f.writeText(format.encodeToString(canswer))
        println(canswer)
    }
    println("Hello, world!")

}

class Caption
