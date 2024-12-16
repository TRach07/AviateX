import org.scalatest.funsuite.AnyFunSuite
import model._
import storage.DataStorage

class Tests extends AnyFunSuite {

  test("Parse country from CSV") {
    val line = "302672,\"AD\",\"Andorra\",\"EU\",\"http://en.wikipedia.org/wiki/Andorra\","
    val country = Country.fromCsv(line)
    assert(country.contains(Country(302672, "AD", "Andorra", "EU")))
  }

  test("Query airports by country") {
    val storage = new DataStorage("countries.csv", "airports.csv", "runways.csv")
    val results = storage.queryAirportsByCountry("AD")
    assert(results.nonEmpty)
  }
}
