package lab3

import org.json4s.JsonDSL._
import org.json4s._

class InfoCities(
                  var worldRank: Int,
                  var city: String,
                  var country: String,
                  var population: String,
                  var metroPopulation: String
                ) {

  def toJson(): JsonAST.JValue = { //return json-object, here use json4s library //pom(dependencies)
    val obj =
      ("worldRank" -> worldRank) ~
        ("city" -> city) ~
        ("country" -> country) ~
        ("population" -> population) ~
        ("metroPopulation" -> metroPopulation)
    obj
  }

}
