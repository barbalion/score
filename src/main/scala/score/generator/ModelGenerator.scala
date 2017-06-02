package score.generator

import java.util.Date

import score.SystemContext
import score.meta._
import score.meta.model.{CompiledModel, Model}

object ModelGenerator extends App {
  var dirName: String = "templates/main.stg"
  var catalog: String = null
  var templates: List[String] = Nil

  def parseArgs(args: List[String]) {
    args match {
      case Nil =>
      case "-catalog" :: v :: tail =>
        catalog = v
        parseArgs(tail)
      case "-dir" :: v :: tail =>
        dirName = v
        parseArgs(tail)
      case "-template" :: v :: tail =>
        templates = v :: templates
        parseArgs(tail)
      case other => sys.error("Illegal parameter: " + other)
    }
  }

  parseArgs(args.toList)

  if (templates.isEmpty)
    templates = "Main" :: Nil

  val model = if (catalog == null) CompiledModel else Model.loadFromCatalog(catalog)

  val gen = new Generator(dirName, templates)(SystemContext)
  gen.process(st => {
    st.add("entities", ScoreModelAdapter.convertValuesForST(model.entities))
    st.add("timestamp", new Date())
  })
}

