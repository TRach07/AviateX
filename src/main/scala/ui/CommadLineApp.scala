package ui

import query.QueryEngine
import storage.DataStorage

object CommandLineApp {

  def main(args: Array[String]): Unit = {
    val storage = new DataStorage("countries.csv", "airports.csv", "runways.csv")
    val queryEngine = new QueryEngine(storage)

    println("Bienvenue dans SkyPaths !")
    println("Choisissez une option :")
    println("1. Query")
    println("2. Report")

    val option = scala.io.StdIn.readLine().trim

    option match {
      case "1" =>
        println("Entrez le nom ou le code du pays:")
        val input = scala.io.StdIn.readLine().trim
        queryEngine.queryAirports(input)

      case "2" =>
        queryEngine.displayReports()

      case _ =>
        println("Option invalide.")
    }
  }
}
