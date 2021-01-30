package lab3

import org.json4s.JsonDSL._
import org.json4s._

class InfoCities(
                  var city: String,
                  var country: String,
                  var worldRank: Int,
                  var population: Int,
                  var surfaceArea: Int,
                  var urbanAreaPopulation: Int,
                  var dateOfCensus: String
                ) {

  def toJson(): JsonAST.JValue= {      //return json-object, here we use json4s library, это в файле pom(dependencies)
    /** Create json object from this object */
    val obj =
      ("city" -> city) ~
        ("country" -> country) ~
        ("worldRank" -> worldRank) ~
        ("population" -> population) ~
        ("surfaceArea" -> surfaceArea) ~
        ("urbanAreaPopulation" -> urbanAreaPopulation) ~
        ("dateOfCensus" -> dateOfCensus)
    obj
  }

}
