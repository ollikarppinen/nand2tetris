package assembler

import java.io.{File, PrintWriter}
import scala.io.Source

object Assembler {
  def main(args: Array[String]): Unit = {
    if(args.isEmpty) {
      println("Give path to file you want to compile.")
    } else {
      assemble(args(0))
    }
  }

  def assemble(path: String): Unit = {
    val file = new java.io.File(path)
    if(!file.exists) println("Invalid path.")
    else if(!file.isFile) println("Can't read file.")
    else if(!path.endsWith(".asm")) println("File is not in .asm format")
    else {
      val fileName = path.split('/').last.dropRight(4)
      val newFile = new File(fileName + ".hack")
      val pw = new PrintWriter(newFile)
      Parser.addLabels(Source.fromFile(path).getLines())
      for(line <- Source.fromFile(path).getLines()) {
        val parsed = Parser.parseLine(line)
        if(parsed.isDefined) {
          pw.write(parsed.get + '\n')
        }
      }
      pw.close()
      println("Assembly completed!")
    }
  }
}
