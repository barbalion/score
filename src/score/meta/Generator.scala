package score.meta

import org.stringtemplate.v4._
import java.io.{BufferedWriter, OutputStreamWriter, FileWriter, Writer}
import java.util.{HashMap, Date, Locale}
import java.lang.{Class, String}
import misc.{Misc, ObjectModelAdaptor}

class Generator(templateGroup: String, templateNames: List[String]){
  val gr: STGroup = new STGroupFile(templateGroup);
  gr.registerRenderer(classOf[String], new CustomRenderer(), false);
  gr.registerModelAdaptor(classOf[Entity], new ScoreModelAdapter())

  private val templates = templateNames map (name => {val st = gr.getInstanceOf(name); if (st == null) sys.error("Template loading error.") else st})
  private val out = new FileSwitchWriter(new OutputStreamWriter(Console.out))

  private class FileSwitchWriter(val defaultWriter: Writer) extends Writer {
    val bufferedThis = new BufferedWriter(FileSwitchWriter.this,
      1000 // todo
    )
    val STWriter = new NoIndentWriter(bufferedThis)

    var currentWriter = defaultWriter;

    def switchTo(aWriter: Writer) {
      bufferedThis.flush();
      if (!(currentWriter eq defaultWriter)) currentWriter.close()
      currentWriter = if (aWriter eq null) defaultWriter else aWriter
    }

    def close() {
      bufferedThis.close();
      currentWriter.close()
    }

    def flush() {
      currentWriter.flush();
    }

    def write(cbuf: Array[Char], off: Int, len: Int) {currentWriter.write(cbuf, off, len)}
  }

  private class CustomRenderer extends AttributeRenderer {
    def toString(o: AnyRef, formatString: String, locale: Locale) = {
      formatString match {
        case "setOutputFile" => {
          out.switchTo(
            if (o == null || o.toString.trim().length() == 0) null else new FileWriter(o.toString, false));
          null
        }
        case _ => o.toString
      }
    }
  }

  def addStParams(st: ST, o: Any) {
    st.add("o", o match {
      case s: Seq[Any] => s.toArray[Any]
      case m: Map[Any, Any] => m.asInstanceOf[HashMap[Any, Any]]
      case other => other
    })
    st.add("timestamp", new Date())
  }

  def process(o: Any, context: Context) = {
    templates map (st => {
      addStParams(st, o)
      st.write(out.STWriter, context.session.locale)
      out.switchTo(null)
    })
  }

}

class ScoreModelAdapter extends ObjectModelAdaptor {
  override def lookupMethod(o: AnyRef, propertyName: String, value: AnyRef, c: Class[_]) = {
    val m = Misc.getMethod(c, propertyName)
    if (m == null)
      super.lookupMethod(o, propertyName, value, c)
    else
      Misc.invokeMethod(m, o, value)
  }
}

object ModelGenerator extends App {
  override def main(args: Array[String]) {
    var dirName: String = "templates/model.stg";
    var catalog: String = null;
    var templates: List[String] = Nil;

    def parseArgs (args: List[String]){
      args match {
        case Nil => return
        case "-catalog" :: v :: tail => {catalog = v; parseArgs(tail)}
        case "-dir" :: v :: tail => {dirName = v; parseArgs(tail)}
        case "-template" :: v :: tail => {templates = v :: templates; parseArgs(tail)}
        case other => sys.error("Illegal parameter: " + other)
      }
    }
    parseArgs(args.toList);

    if (templates.isEmpty)
      templates = "Main" :: Nil

    val gen = new Generator(dirName, templates)
    val model = if (catalog == null) CompiledModel else Model.loadFromCatalog(catalog)

    gen.process(model.entities, SystemContext)
  }
}