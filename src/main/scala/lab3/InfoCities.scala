package lab3

import org.json4s.JsonDSL._
import org.json4s._

class InfoCities(
                  var worldRank: String,
                  var city: String,
                  var country: String,
                  var population: String,
                  var metroPopulation: String
                ) {

  def pToint(): Int = {
    val pop = population.replace(",", "")
    pop.toInt
  }

  def mpToint(): Int = {
    val toRemove = ",. ".toSet
    val mpop = if (metroPopulation == "") 0 else metroPopulation.filterNot(toRemove).toInt
    mpop
  }

  def toJson(): JsonAST.JValue = { //return json-object, here use json4s library //pom(dependencies)
    val obj =
      ("worldRank" -> worldRank.toInt) ~
        ("city" -> city) ~
        ("country" -> country) ~
        ("population" -> pToint()) ~
        ("metroPopulation" -> mpToint())
    obj
  }

}
