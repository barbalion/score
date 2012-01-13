package scoro.meta

import java.io.File

class Generator {
  def processFile(f: File) {

  }

}

object Generator extends Generator with App {
  override def main(args: Array[String]) {
    if (args.length == 0)
      sys.error("Please use: Generator.class [template_file_name.stg].")

    val f = new File(args(0))
    if (!f.exists())
      sys.error("File not found.")
  }
}