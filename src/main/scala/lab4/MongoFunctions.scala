package lab4


import scala.io.Source
import MongoHelpers._
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._


class MongoFunctions {

  def getCollection(): MongoCollection[Document] = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost")
    val database: MongoDatabase = mongoClient.getDatabase("cities")
    val collection: MongoCollection[Document] = database.getCollection("worldCities")
    if (collection.count().results()(0) == 0) {
      val documents = (0 to 299) map { i: Int => Document(Source.fromFile("src/main/resources/jsons/" + i + ".json").mkString) }
      collection.insertMany(documents).results()
    }
    collection
  }

  def printTop20(collection: MongoCollection[Document]): Unit = {
    println("Вывести ТОП-20 из мирового рейтинга")
    collection.find(lt("worldRank", "21"))
      .projection(fields(include("worldRank", "city", "country", "population", "metroPopulation"), excludeId()))
      .sort(descending("worldRank"))
      .limit(20)
      .printResults()
    println()
  }

  def printRussianCities(collection: MongoCollection[Document]): Unit = {
    println("Вывести все города Росии")
    collection.find(equal("country", "Russia"))
      .projection(fields(include("worldRank", "city", "country", "population", "metroPopulation"), excludeId()))
      .sort(descending("worldRank"))
      .limit(30)
      .printResults()
    println()
  }


  def print10biggestCities(collection: MongoCollection[Document]): Unit = {
    println("")

  }

  def printBet6and8kPop(collection: MongoCollection[Document]): Unit = {

  }

}
