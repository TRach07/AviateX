package storage
import model._
import org.apache.commons.lang3.StringUtils
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
    }.reverse 
  }
  source.close()
  result.getOrElse(Nil)
  }

  def findCountryByFuzzyMatch(input: String): Option[Country] = {
    val threshold = 0.7
    val exactMatch = countries.find(c => c.name.equalsIgnoreCase(input))

    exactMatch match {
      case Some(country) => return Some(country)
      case None =>
    }

    countries.foreach { c =>
      val distance = StringUtils.getJaroWinklerDistance(input.toLowerCase, c.name.toLowerCase)
    }

    val fuzzyMatches = countries
      .map(c => (c, StringUtils.getJaroWinklerDistance(input.toLowerCase, c.name.toLowerCase)))
      .filter { case (_, distance) => distance >= threshold }
      .sortBy(-_._2)

    fuzzyMatches.headOption match {
      case Some((country, distance)) => Some(country)
      case None => None
    }
  }

  def queryAirportsByCountry(input: String): List[(Airport, List[Runway])] = {
    val countryExact = countries.find(c => c.code.equalsIgnoreCase(input) || c.name.equalsIgnoreCase(input))
    val countryFuzzy = countryExact.orElse {
      countries
        .map(c => (c, StringUtils.getJaroWinklerDistance(c.name.toLowerCase, input.toLowerCase)))
        .filter(_._2 > 0.7)
        .sortBy(-_._2)
        .headOption
        .map(_._1)
    }

    countryFuzzy.toList.flatMap { c =>
      val countryAirports = airports.filter(_.countryCode == c.code)
      countryAirports.map { airport =>
        val associatedRunways = runways.filter(_.airportId == airport.id)
        (airport, associatedRunways)
      }
    }
  }

  def getCountriesWithHighestAndLowestAirports(): (List[(Country, Int)], List[(Country, Int)]) = {
    val airportCounts = airports.groupBy(_.countryCode).mapValues(_.size).toList
    val highest = airportCounts.sortBy(-_._2).take(10)
    val lowest = airportCounts.sortBy(_._2).take(10)
    val countriesWithCounts = highest.map { case (code, count) => (countries.find(_.code == code).get, count) }
    val lowestCountriesWithCounts = lowest.map { case (code, count) => (countries.find(_.code == code).get, count) }
    (countriesWithCounts, lowestCountriesWithCounts)
  }

  def runwayTypesPerCountry(): Map[String, List[String]] = {
    val runwaysPerCountry = airports.map { airport =>
      val country = countries.find(_.code == airport.countryCode).map(_.name).getOrElse("")
      (country, runways.filter(_.airportId == airport.id).map(_.surface).distinct)
    }
    runwaysPerCountry.groupBy(_._1).mapValues(_.flatMap(_._2).distinct).toMap
  }

  def commonRunwayLatitudes(): List[String] = {
    runways.flatMap(r => r.leIdent).groupBy(identity).mapValues(_.size).toList.sortBy(-_._2).take(10).map(_._1)
  }
}