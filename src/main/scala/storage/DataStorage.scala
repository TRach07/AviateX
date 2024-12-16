package storage
import model._
import scala.util.Try

class DataStorage(countriesFile: String, airportsFile: String, runwaysFile: String) {

  val countries: List[Country] = parseFile(countriesFile, Country.fromCsv)
  val airports: List[Airport] = parseFile(airportsFile, Airport.fromCsv)
  val runways: List[Runway] = parseFile(runwaysFile, Runway.fromCsv)

  private def parseFile[T](fileName: String, parser: String => Option[T]): List[T] = {
    val source = scala.io.Source.fromResource(fileName)
    val result = Try {
      source.getLines().drop(1).foldLeft(List.empty[T]) { (acc, line) =>
        parser(line) match {
          case Some(value) => value :: acc
          case None => acc
        }
      }.reverse // Pour préserver l'ordre des lignes
    }
    source.close()
    result.getOrElse(Nil)
  }

  def queryAirportsByCountry(input: String): List[(Airport, List[Runway])] = {
    val country = countries.find(c => c.code.equalsIgnoreCase(input) || c.name.equalsIgnoreCase(input))
    country.toList.flatMap { c =>
      val countryAirports = airports.filter(_.countryCode == c.code)
      countryAirports.map { airport =>
        val associatedRunways = runways.filter(_.airportId == airport.id)
        (airport, associatedRunways)
      }
    }
  }
}
