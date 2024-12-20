import org.scalatest.funsuite.AnyFunSuite
import model._
import storage.DataStorage



class FuzzyQueryTests extends AnyFunSuite {

  // Chargement des données fictives pour les tests
  val countries = List(
    Country(1, "US", "United States", "NA"),
    Country(2, "FR", "France", "EU"),
    Country(3, "ZA", "South Africa", "AF"),
    Country(4, "ZW", "Zimbabwe", "AF")
  )

  val airports = List(
    Airport(1, "Los Angeles International", "US"),
    Airport(2, "Charles de Gaulle", "FR"),
    Airport(3, "Cape Town International", "ZA")
  )

  val runways = List(
    Runway(1, 1, "ASPH", Some("H1")),
    Runway(2, 2, "GRVL", None),
    Runway(3, 3, "TURF", Some("01"))
  )

  // Simuler un stockage fictif
  val storage = new DataStorageMock(countries, airports, runways)

  test("Recherche exacte - nom de pays") {
    val results = storage.queryAirportsByCountry("France")
    assert(results.size == 1)
    assert(results.head._1.name == "Charles de Gaulle")
  }

  test("Recherche exacte - code de pays") {
    val results = storage.queryAirportsByCountry("ZA")
    assert(results.size == 1)
    assert(results.head._1.name == "Cape Town International")
  }

  test("Recherche floue réussie") {
  val storage = new DataStorage("countries.csv", "airports.csv", "runways.csv")
  val results = storage.findCountryByFuzzyMatch("zimb")
  assert(results.isDefined) // Vérifier qu'il y a au moins un résultat
  assert(results.get.name == "Zimbabwe") // Valider que le résultat attendu est "Zimbabwe"
  }

  test("Recherche floue infructueuse") {
    val results = storage.queryAirportsByCountry("Xyz")
    assert(results.isEmpty)
  }

  test("Priorité des correspondances exactes") {
    val results = storage.queryAirportsByCountry("ZA")
    assert(results.size == 1)
    assert(results.head._1.name == "Cape Town International") // Correspondance exacte, pas floue
  }
}

// Mock de DataStorage pour les tests
class DataStorageMock(
  mockCountries: List[Country],
  mockAirports: List[Airport],
  mockRunways: List[Runway]
) extends DataStorage("", "", "") {
  override val countries: List[Country] = mockCountries
  override val airports: List[Airport] = mockAirports
  override val runways: List[Runway] = mockRunways
}