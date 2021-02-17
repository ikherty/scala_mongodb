import java.io._

import lab3.InfoCities
import org.json4s.jackson.JsonMethods._
import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class ParseHelper { //get string from html code
  def getPageContent(url: String): String = {
    /** Get all html page from url */
    val content = Source.fromURL(url)
    content.mkString
  }

  def textBetween(content: String, start: String, end: String): String = { //substring
    /** Get some text  from html */
    val pos1 = content.indexOf(start, 0)
    val pos2 = content.indexOf(end, pos1)
    content.substring(pos1, pos2)
  }

  def getInfoCities(): Array[InfoCities] = { //получение массива с необработанными данными
    //считываем страницу------------------------------
    val content = getPageContent(url = "http://www.citymayors.com/statistics/largest-cities-country-by-country.html") // получение html из строк
    val table = textBetween(content = content,
      start = "<table width=\"515\" border=\"1\" cellspacing=\"2\" cellpadding=\"0\">", //поиск кода в html
      end = "</table>") // получение таблицы с информацией
    //-------------------------------------------
    val parsedTable = Jsoup.parse(table) // парс данных с библиотекой Jsoup
    val rows = parsedTable.select("tr") // get Elements (collection as array) consist of rows body table
    val infoCitiesArray = new ArrayBuffer[InfoCities] // create arraybuffer for all city info ( will be 500 elements)
    for (i <- 0 until rows.size()) { // let start iterate by rows table
      val row = rows.get(i) // get one row
      val cols = row.select("td") // get all column in row
      val InfoCities = new InfoCities( // set info from row in object CitiesInfo
        worldRank = cols.get(0).text(),
        city = cols.get(1).text(),//0
        country = cols.get(2).text(),//исправить для ИНТ childNodeSize() 1
        population = cols.get(3).text(),
        mp = cols.get(4).text()
      )
      infoCitiesArray += InfoCities // add one info in all info
    }
    infoCitiesArray.toArray // return array with all information about regular city
  }

  def save(infoCitiesArray: Array[InfoCities]): Unit = { //save json-files
    /** Save every flight info in separate file */
    for (i <- 0 until infoCitiesArray.length) //for in array
    {
      val pw = new PrintWriter(new File("src/main/resources/jsons/" + i + ".json"))
      pw.write(compact(render(infoCitiesArray(i).toJson()))) //every object of Info
      pw.close()
    }

  }


}
