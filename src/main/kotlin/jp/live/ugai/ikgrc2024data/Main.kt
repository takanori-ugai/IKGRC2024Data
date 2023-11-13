import org.apache.jena.query.DatasetFactory
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.ResultSet
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.RDFDataMgr
import org.apache.jena.update.UpdateExecutionFactory
import org.apache.jena.update.UpdateFactory
import org.apache.jena.update.UpdateProcessor
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.random.Random

fun main(args: Array<String>) {
    val make = Main()
    val fileNames = if (args.size > 0) { args } else { arrayOf("Data/Admire_art1_scene1.ttl") }
    for (fileName in fileNames) {
        make.makeData(fileName, 2, 0, 0)
        make.makeData(fileName, 5, 0, 0)
        make.makeData(fileName, 10, 0, 0)
        make.makeData(fileName, 2, 2, 0)
        make.makeData(fileName, 2, 2, 2)
        make.makeData(fileName, 5, 5, 0)
        make.makeData(fileName, 5, 5, 5)
        make.makeData(fileName, 10, 10, 0)
        make.makeData(fileName, 10, 10, 10)
    }
    // create an empty model
    // create an empty model
    // val fileName = "Admire_art1_scene1"
    // val placeRate = 10
    // val actionRate = 10
    // val objectRate = 0
}

class Main {

    fun makeData(fileName: String, placeRate: Int, actionRate: Int, objectRate: Int) {
        val model: Model = ModelFactory.createDefaultModel()

// use the RDFDataMgr to find the input file
        val inputStream: InputStream = RDFDataMgr.open("file:$fileName")
        model.read(inputStream, null, "TURTLE")

        for (addName in listOf("add_classes.ttl", "add_places.ttl", "vh2kg_schema.ttl")) {
            val model2: Model = ModelFactory.createDefaultModel()
            val inputStream2: InputStream = RDFDataMgr.open("file:Data/$addName")
            model2.read(inputStream2, null, "TURTLE")
            model.add(model2)
        }

        val queryString = """
PREFIX ex: <http://kgrc4si.home.kg/virtualhome2kg/instance/>
PREFIX : <http://kgrc4si.home.kg/virtualhome2kg/ontology/>
PREFIX vh2kg: <http://kgrc4si.home.kg/virtualhome2kg/ontology/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX time: <http://www.w3.org/2006/time#>
select DISTINCT * where {
    ?ee :hasEvent ?event .
    ?event :action ?action .
    OPTIONAL {
    ?event vh2kg:place ?place .
    }
    ?event vh2kg:time ?time .
    ?time time:numericDuration ?dtime .
    ?event vh2kg:eventNumber ?number .
    OPTIONAL {
    ?event :mainObject ?mainObject .
    } 
    OPTIONAL {
    ?event :targetObject ?targetObject .
    } 
#    ?event ?p ?object .
}order by (?number)
    """

        val dQueries: MutableList<String> = mutableListOf()
        val iQueries: MutableList<String> = mutableListOf()
        QueryExecutionFactory.create(queryString, model).use { qexec ->
            var results: ResultSet? = qexec.execSelect()
//        results = ResultSetFactory.copyResults(results)
//        println(results.)
            results!!.forEach {
                val deleteQuery = StringBuilder(
                    """
PREFIX ex: <http://kgrc4si.home.kg/virtualhome2kg/instance/>
PREFIX : <http://kgrc4si.home.kg/virtualhome2kg/ontology/>
PREFIX vh2kg: <http://kgrc4si.home.kg/virtualhome2kg/ontology/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX time: <http://www.w3.org/2006/time#>
DELETE DATA {
    """
                )
                val insertQuery = StringBuilder(
                    """
PREFIX ex: <http://kgrc4si.home.kg/virtualhome2kg/instance/>
PREFIX : <http://kgrc4si.home.kg/virtualhome2kg/ontology/>
PREFIX vh2kg: <http://kgrc4si.home.kg/virtualhome2kg/ontology/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX time: <http://www.w3.org/2006/time#>
INSERT DATA {
    """
                )
                val place = it["place"]
                val number = it["number"].asLiteral().int
                println(number)
                if (place != null && Random.nextInt(10) < placeRate) {
                    deleteQuery.append("<${it["event"]}> vh2kg:place <$place> .\n")
                    insertQuery.append("<${it["event"]}> vh2kg:place <http://kgrc4si.home.kg/virtualhome2kg/instance/PlaceXXX$number> .\n")
                }
                val mainObject = it["mainObject"]
                if (mainObject != null && Random.nextInt(10) < objectRate) {
                    deleteQuery.append("<${it["event"]}> :mainObject <$mainObject> .\n")
                    insertQuery.append("<${it["event"]}> :mainObject <http://kgrc4si.home.kg/virtualhome2kg/instance/MainObjectXXX$number> .\n")
                }
                val targetObject = it["targetObject"]
                if (targetObject != null && Random.nextInt(10) < objectRate) {
                    deleteQuery.append("<${it["event"]}> :targetObject <$targetObject> .\n")
                    insertQuery.append("<${it["event"]}> :targetObject <http://kgrc4si.home.kg/virtualhome2kg/instance/TargetObjectXXX$number> .\n")
                }
                if (Random.nextInt(10) < actionRate) {
                    deleteQuery.append("<${it["event"]}> :action <${it["action"]}> .\n")
                    insertQuery.append("<${it["event"]}> :action <http://kgrc4si.home.kg/virtualhome2kg/ontology/action/ActionXXX$number> .\n")
                }
                deleteQuery.append("}\n")
                insertQuery.append("}\n")
//            println(it["place"])
                dQueries.add(deleteQuery.toString())
                iQueries.add(insertQuery.toString())
//            println(it)
            }

            // Create an UpdateRequest

            // Create a Dataset and add the Model to it
//        return results // Passes the result set out of the try-resources
        }
        val dataset = DatasetFactory.create(model)
        for (query in dQueries) {
            val updateRequest = UpdateFactory.create(query)
            val updateProcessor: UpdateProcessor = UpdateExecutionFactory.create(updateRequest, dataset)
            updateProcessor.execute()
        }
        for (query in iQueries) {
            val updateRequest = UpdateFactory.create(query)
            val updateProcessor: UpdateProcessor = UpdateExecutionFactory.create(updateRequest, dataset)
            updateProcessor.execute()
        }

        // Execute the UpdateRequest on the Dataset

//    println(dQueries)
//    println(iQueries)
        QueryExecutionFactory.create(queryString, model).use { qexec ->
            var results: ResultSet? = qexec.execSelect()
//        results = ResultSetFactory.copyResults(results)
//        println(results.)
            results!!.forEach {
                println(it)
            }

// write it to standard out
            val fname = fileName.replace(".ttl", "")
            val outputStream = FileOutputStream("$fname-$placeRate$actionRate$objectRate.ttl")
            model.write(outputStream, "TURTLE")
        }
    }
}
