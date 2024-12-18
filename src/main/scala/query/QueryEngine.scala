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

  def displayReports(): Unit = {
    val (highest, lowest) = storage.getCountriesWithHighestAndLowestAirports()
    println("Pays avec le plus d'aéroports :")
    highest.foreach { case (country, count) =>
      println(s"${country.name} : $count")
    }
    println("\nPays avec le moins d'aéroports :")
    lowest.foreach { case (country, count) =>
      println(s"${country.name} : $count")
    }

    println("\nTypes de pistes par pays :")
    val runwayTypes = storage.runwayTypesPerCountry()
    runwayTypes.foreach { case (country, types) =>
      println(s"$country : ${types.mkString(", ")}")
    }

    println("\nTop 10 des latitudes de pistes communes :")
    storage.commonRunwayLatitudes()
    .foreach(println)
  }
}
