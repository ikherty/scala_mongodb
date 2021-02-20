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

  def stringToint(): Int = {
    val num = population.replace(",", "")
    num.toInt
  }

  def toJson(): JsonAST.JValue = { //return json-object, here use json4s library //pom(dependencies)
    val obj =
      ("worldRank" -> worldRank.toInt) ~
        ("city" -> city) ~
        ("country" -> country) ~
        ("population" -> stringToint()) ~
        ("metroPopulation" -> stringToint())
    obj
  }

}
