package scoro.meta

import java.util.{Date, Locale}
import org.stringtemplate.v4._
import java.io.{BufferedWriter, OutputStreamWriter, FileWriter, Writer}

class Generator(templateDir: String, templateNames: List[String]){
  val gr: STGroup = new STGroupFile(templateDir);
  gr.registerRenderer(classOf[String], new CustomRenderer(), false);

  private val templates = templateNames map (gr.getInstanceOf(_))
  private val out = new FileSwitchWriter(new OutputStreamWriter(Console.out))

  private class FileSwitchWriter(val defaultWriter: Writer) extends Writer {
    val bufferedThis = new BufferedWriter(FileSwitchWriter.this)
    val STWriter = new NoIndentWriter(bufferedThis)

    var currentWriter = defaultWriter;

    def switchTo(aWriter: Writer) {
      bufferedThis.flush()
      currentWriter = if (aWriter eq null) defaultWriter else aWriter
    }

    def close() {currentWriter.close()}

    def flush() {currentWriter.flush()}

    def write(cbuf: Array[Char], off: Int, len: Int) {write(cbuf, off, len)}
  }

  private class CustomRenderer extends AttributeRenderer {
    def toString(o: AnyRef, formatString: String, locale: Locale) = {
      formatString match {
        case "setOutputFile" => {
          out.switchTo(
            if (o == null || o.toString.trim().length() == 0) null else new FileWriter(o.toString, false));
          null
        }
      }
    }
  }

  def addStParams(st: ST, o: Any) {
    st.add("o", o)
    st.add("timestamp", new Date())
  }

  def process(o: Any, context: Context[Entity]) = {
    templates map (st => {
      addStParams(st, o)
      st.write(out.STWriter, context.session.locale)
    })
  }

}

object ModelGenerator extends App {
  override def main(args: Array[String]) {
    var dirName: String = "templates";
    var catalog: String = "catalog.xml";
    var templates: List[String] = Nil;

    def parseArgs (args: Seq[String]){
      args match {
        case Nil => return
        case "-catalog" :: v :: tail => {catalog = v; parseArgs(tail)}
        case "-dir" :: v :: tail => {dirName = v; parseArgs(tail)}
        case "-template" :: v :: tail => {templates = v :: templates; parseArgs(tail)}
        case other => sys.error("Illegal parameter: " + other)
      }
    }
    if (templates.isEmpty)
      templates = "Main" :: Nil

    val gen = new Generator(dirName, templates)
    val model = Model.loadFromCatalog(catalog)

    gen.process(model.entities, SystemContext)
  }
}