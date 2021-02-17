package lab3

import org.json4s.JsonDSL._
import org.json4s._

class InfoCities(
                  var worldRank: String, //Int
                  var city: String,
                  var country: String,
                  var population: String, //Int
                  var mp: String //Int
                ) {

  def toJson(): JsonAST.JValue = { //return json-object, here we use json4s library, это в файле pom(dependencies)
    /** Create json object from this object */
    val obj =
      ("worldRank" -> worldRank) ~
        ("city" -> city) ~
        ("country" -> country) ~
        ("population" -> population) ~
        ("mp" -> mp)
        obj
  }

}
