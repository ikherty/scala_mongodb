object mainLab3 extends App {
  val parseHelper = new ParseHelper()
  val infoCitiesArray = parseHelper.getInfoCities()
  parseHelper.save(infoCitiesArray)
}