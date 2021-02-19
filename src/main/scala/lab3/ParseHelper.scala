import java.io._

import lab3.InfoCities
import org.json4s.jackson.JsonMethods._
import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class ParseHelper {
  def getPageContent(url: String): String = {
    val content = Source.fromURL(url)
    content.mkString
  }

  def textBetween(content: String, start: String, end: String): String = {
    val pos1 = content.indexOf(start, 0)
    val pos2 = content.indexOf(end, pos1)
    content.substring(pos1, pos2)
  }

  def getInfoCities(): Array[InfoCities] = {
    val content = getPageContent(url = "http://www.citymayors.com/statistics/largest-cities-country-by-country.html")
    val table = textBetween(content = content,
      start = "<table width=\"515\" border=\"1\" cellspacing=\"2\" cellpadding=\"0\">",
      end = "</table>")
    //-------------------------------------------
    val parsedTable = Jsoup.parse(table)
    val rows = parsedTable.select("tr") // get elements (collection as array) consist of rows body table
    val infoCitiesArray = new ArrayBuffer[InfoCities]
    for (i <- 1 until rows.size()) {
      val row = rows.get(i)
      val cols = row.select("td") // get all column in row
      val InfoCities = new InfoCities(
        worldRank = cols.get(0).text().toInt,
        city = cols.get(1).text(),
        country = cols.get(2).text(),
        population = cols.get(3).text(),
        metroPopulation = cols.get(4).text()
      )
      infoCitiesArray += InfoCities
    }
    infoCitiesArray.toArray
  }

  def save(infoCitiesArray: Array[InfoCities]): Unit = {
    for (i <- 0 until infoCitiesArray.length) {
      val pw = new PrintWriter(new File("src/main/resources/jsons/" + i + ".json"))
      pw.write(compact(render(infoCitiesArray(i).toJson())))
      pw.close()
    }

  }


}
