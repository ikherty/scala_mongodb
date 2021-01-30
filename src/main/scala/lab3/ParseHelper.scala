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

  def getInfoCities(): Array[InfoCities] = { //get array of dirty data
    //считываем страницу------------------------------
    val content = getPageContent(url = "https://data.mongabay.com/cities_pop_01.htm") // get html page in string
    val table = textBetween(content = content,
      start = "<table width=\"100 % \" class=\"boldtable\">", //found in html, exact this table
      end = "</table>") // get table with flight info
    //-------------------------------------------
    val parsedTable = Jsoup.parse(table) // get object which prepared for parsing via library Jsoup
    val rows = parsedTable.select("tbody tr") // get Elements (collection as array) consist of rows body table
    val infoCitiesArray = new ArrayBuffer[InfoCities] // create arraybuffer for all flight info ( will be 500 elements)
    for (i <- 0 until rows.size()) { // let start iterate by rows table
      val row = rows.get(i) // get one row
      val cols = row.select("td") // get all column in row
      val InfoCities = new InfoCities( // set info from row in object FlightInfo
        city = cols.get(0).text(),
        country = cols.get(0).text(),
        worldRank = cols.get(1).childNodeSize(),//исправить для ИНТ
        population = cols.get(8).childNodeSize(),
        surfaceArea = cols.get(9).childNodeSize(),
        urbanAreaPopulation = cols.get(10).childNodeSize(),
        dateOfCensus = cols.get(13).text()
      )
      infoCitiesArray += InfoCities // add one info in all info
    }
    infoCitiesArray.toArray // return array with all information about regular flight
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
