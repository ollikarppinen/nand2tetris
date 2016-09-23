package assembler

object Parser {
  def addLabels(lines: Iterator[String]) = {
    var address = 0
    for(line <- lines) {
      val t = trim(line)
      if(isLabel(t)) SymbolTable.addLabel(parseLabel(t), address)
      else if(isInstruction(t)) address += 1
    }
  }
  def parseLabel(x: String): String = x.substring(x.indexOf('(') + 1, x.indexOf(')'))
  def isLabel(x: String): Boolean = x.nonEmpty && (x.head == '(' && x.last == ')')

  def parseLine(x: String): Option[String] =
    if(isInstruction(x)) Some(parseInstruction(x).toBinary) else None

  def isInstruction(x: String): Boolean = {
    x.replace(" ", "").take(1) match {
      case "" => false
      case "/" => false
      case "(" => false
      case _ => true
    }
  }

  def parseInstruction(i: String): HackInstruction = {
    val t = trim(i)
    t.head match {
      case '@' => parseA(t)
      case _ => parseC(t)
    }
  }

  def parseA(x: String) = {
    val value = x.tail
    if(isNumeric(value)) AInstruction(value.toInt)
    else if(SymbolTable.contains(value)) AInstruction(SymbolTable.getAddress(value))
    else {
      SymbolTable.addVariable(value)
      AInstruction(SymbolTable.getAddress(value))
    }
  }

  def parseC(x: String) = CInstruction(dest(x), comp(x), jump(x))

  def trim(str: String): String = str.split("//").head.replace(" ", "")
  def isNumeric(x: String): Boolean = x.forall(_.isDigit)

  trait HackInstruction {
    def toBinary: String
  }
  case class AInstruction(address: Integer) extends HackInstruction {
    def toBinary = "0" + toBinaryString(address, 15)
    override def toString: String = this.toBinary
  }

  // 1 1 1 a - c1 c2 c3 c4 - c5 c6 d1 d2 - j1 j2 j3
  case class CInstruction(dest: String, comp: String, jump: String) extends HackInstruction{
    def toBinary = "111" + Code.comp(comp) + Code.dest(dest) + Code.jump(jump)
    override def toString: String = this.toBinary
  }

  def toBinaryString(i: Integer, l: Integer) = appendBits(Integer.toBinaryString(i), l)
  def appendBits(b: String, l: Integer) = "0" * (l - b.length) + b

  def dest(x: String) = if(x.contains('=')) x.split('=').head else "null"
  def jump(x: String) = if(x.contains(';')) x.split(';').last else "null"
  def comp(x: String) = x.substring(
    if(x.contains('=')) x.indexOf('=') + 1 else 0,
    if(x.contains(';')) x.indexOf(';') else x.length
  )
}

