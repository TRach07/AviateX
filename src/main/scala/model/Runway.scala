package model

case class Runway(id: Int, airportId: Int, surface: String, leIdent: Option[String])

object Runway {
  def fromCsv(line: String): Option[Runway] = {
    val parts = line.split(",").map(_.trim.stripPrefix("\"").stripSuffix("\""))
    if (parts.length >= 9)
      Some(Runway(parts(0).toInt, parts(1).toInt, parts(5), Option(parts(8)).filter(_.nonEmpty)))
    else None
  }
}
