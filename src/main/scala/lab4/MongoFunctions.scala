package lab4

import scala.io.Source
import MongoHelpers._
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._

class MongoFunctions {

  def getCollection(): MongoCollection[Document] = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost")
    val database: MongoDatabase = mongoClient.getDatabase("cities")
    val collection: MongoCollection[Document] = database.getCollection("worldCities")
    collection.drop().results()
    if (collection.count().results()(0) == 0) {
      val documents = (0 to 299) map { i: Int => Document(Source.fromFile("src/main/resources/jsons/" + i + ".json").mkString) }
      collection.insertMany(documents).results()
    }
    collection
  }

  def printTop20(collection: MongoCollection[Document]): Unit = {
    println("ТОП-20 из мирового рейтинга:")
    collection.find(lt("worldRank", 21))
      .projection(fields(include("worldRank", "city", "country", "population", "metroPopulation"), excludeId()))
      .sort(ascending("worldRank"))
      .limit(20)
      .printResults()
    println()
  }

  def printRussianCities(collection: MongoCollection[Document]): Unit = {
    println("Все города Росcии:")
    collection.find(equal("country", "Russia"))
      .projection(fields(include("worldRank", "city", "country", "population"), excludeId()))
      .sort(ascending("worldRank"))
      .printResults()
    println()
  }

  def printMetroes(collection: MongoCollection[Document]): Unit = {
    println("Список городов-столиц:")
    collection.find(equal("population", "metroPopulation"))
      //collection.find()
      .projection(fields(include("city", "country", "population", "metroPopulation"), excludeId()))
      .sort(ascending("worldRank"))
      .printResults()
    println()
  }

  def print10biggestCities(collection: MongoCollection[Document]): Unit = {
    println("10 городов с наибольшим населением:")
    collection.find(exists("population"))
      .projection(fields(include("city", "country", "population"), excludeId()))
      .sort(descending("population"))
      .limit(10)
      .printResults()
    println()
  }

  def printBet6and8kPop(collection: MongoCollection[Document]): Unit = {
    println("Список городов, где население от 6 до 8 млн человек:")
    collection.find(and(lt("population", 8000000), (gt("population", 6000000))))
      .projection(fields(include("worldRank", "city", "country", "population"), excludeId()))
      .sort(ascending("population"))
      .printResults()
    println()
  }

  def printWOpop(collection: MongoCollection[Document]): Unit = {
    println("Страны с неизвестной численностью населения в столице:")
    collection.find(equal("metroPopulation", 0))
      .projection(fields(include("country", "metroPopulation"), excludeId()))
      .sort(ascending("worldRank"))
      .printResults()
    println()
  }
}
