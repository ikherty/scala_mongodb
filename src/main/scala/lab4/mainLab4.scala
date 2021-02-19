package lab4

import org.mongodb.scala._

object mainLab4 extends App {

  val mongoFunctions = new MongoFunctions()
  val collection = mongoFunctions.getCollection()
  mongoFunctions.printTop20(collection=collection)
  mongoFunctions.printRussianCities(collection=collection)
  //mongoFunctions.print10biggestCities(collection=collection)
  //mongoFunctions.printBet6and8kPop(collection=collection)
  //mongoFunctions.print(collection=collection)
}
