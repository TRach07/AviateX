package query

import storage.DataStorage

class QueryEngine(storage: DataStorage) {

  def queryAirports(input: String): Unit = {
    val results = storage.queryAirportsByCountry(input)
    if (results.isEmpty) {
      println("Aucun résultat trouvé pour ce pays.")
    } else {
      results.foreach { case (airport, runways) =>
        println(s"Aéroport : ${airport.name}")
        runways.foreach(r => println(s"  Piste : Surface = ${r.surface}, Latitude = ${r.leIdent.getOrElse("N/A")}"))
      }
    }
  }
}
