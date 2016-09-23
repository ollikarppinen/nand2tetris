package assembler

object SymbolTable {
  val table = scala.collection.mutable.Map[String, Integer](
    "SP" -> 0,
    "LCL" -> 1,
    "ARG" -> 2,
    "THIS" -> 3,
    "THAT" -> 4,
    "SCREEN" -> 16384,
    "KBD" -> 24576
  )
  (0 to 15).foreach(x => table += ("R" + x -> x))

  var nextAvailableAddress = 16

  def addLabel(symbol: String, address: Integer): Unit = table += (symbol -> address)
  def addVariable(symbol: String): Unit = {
    table += (symbol -> nextAvailableAddress)
    nextAvailableAddress += 1
  }
  def contains(symbol: String): Boolean = table.contains(symbol)
  def getAddress(symbol: String): Integer = table(symbol)
}
