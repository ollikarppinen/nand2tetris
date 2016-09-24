package assembler

import java.io.{File, PrintWriter}
import scala.io.Source

object Assembler {
  def main(args: Array[String]): Unit = {
    if(args.isEmpty) {
      println("No file path argument")
    } else {
      val path = args(0)
      val file = new java.io.File(path)
      if(!file.exists || !file.isFile) {
        println("Invalid path")
      } else if(!path.endsWith(".asm")) {
        println("Wrong file format")
      } else {
        assemble(file)
      }
    }
  }

  def assemble(file: File): Unit = {
    val newFile = new File(file.getName.dropRight(4) + ".hack")

    def getLines = Source.fromFile(file).getLines

    Parser.addLabels(getLines)

    val pw = new PrintWriter(newFile)
    for(line <- getLines) {
      val trimmedLine = Parser.trim(line)
      if(Parser.isInstruction(trimmedLine)) pw.write(Parser.parseLine(trimmedLine))
    }
    pw.close()
    println("Assembly completed!")
  }
}
