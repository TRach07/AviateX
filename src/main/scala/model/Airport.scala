package model

case class Airport(id: Int, name: String, countryCode: String)

object Airport {
  def fromCsv(line: String): Option[Airport] = {
    val parts = line.split(",").map(_.trim.stripPrefix("\"").stripSuffix("\""))
    if (parts.length >= 9) Some(Airport(parts(0).toInt, parts(3), parts(8))) else None
  }
}
