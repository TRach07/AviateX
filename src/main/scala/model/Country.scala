package model

case class Country(id: Int, code: String, name: String, continent: String)

object Country {
  def fromCsv(line: String): Option[Country] = {
    val parts = line.split(",").map(_.trim.stripPrefix("\"").stripSuffix("\""))
    if (parts.length >= 4) Some(Country(parts(0).toInt, parts(1), parts(2), parts(3))) else None
  }
}
