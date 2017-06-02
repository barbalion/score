package score.generator

import java.io._
import java.util.Locale

import org.apache.commons.io.FileUtils
import org.stringtemplate.v4._
import score.Context

class Generator(templateGroup: String, templateNames: List[String])(implicit val context: Context) {
  val gr: STGroup = new STGroupFile(templateGroup)
  registerStHandlers(gr)

  def registerStHandlers(gr: STGroup) {
    gr.registerRenderer(classOf[String], new CustomAttributeRenderer(), true)
    gr.registerRenderer(classOf[Any], new DefaultValueRenderer())
    gr.registerModelAdaptor(classOf[Object], new ScoreModelAdapter())
  }

  private val templates = templateNames map (name => {
    val st = gr.getInstanceOf(name)
    if (st == null) sys.error("Template loading error.") else st
  })
  private val out = new FileSwitchWriter(new OutputStreamWriter(scala.Console.out))

  private class FileSwitchWriter(val defaultWriter: Writer) extends Writer {
    val bufferedThis = new BufferedWriter(FileSwitchWriter.this)
    val STWriter = new AutoIndentWriter(bufferedThis)

    var currentWriter = defaultWriter

    def switchTo(aWriter: Writer) {
      bufferedThis.flush()

      if (!(currentWriter eq defaultWriter))
        currentWriter.close()

      currentWriter = if (aWriter eq null) defaultWriter else aWriter
    }

    def close() {
      bufferedThis.close()
      currentWriter.close()
    }

    def flush() {
      currentWriter.flush()
    }

    def write(cbuf: Array[Char], off: Int, len: Int) {
      currentWriter.write(cbuf, off, len)
    }
  }

  private class CustomAttributeRenderer extends ScoreAttributeRenderer {
    private var fileNameWriter: StringWriter = null

    override def toString(o: AnyRef, formatString: String, locale: Locale) = {
      formatString match {
        case "OutputFile" =>
          o.toString match {
            case "start" => fileNameWriter = new StringWriter(); out.switchTo(fileNameWriter)
            case "end" if fileNameWriter != null =>
              out.bufferedThis.flush()
              val fileName: String = fileNameWriter.getBuffer.toString.trim()
              val f = new File(fileName)
              if (f.getParent != null)
                FileUtils.forceMkdir(new File(f.getParent))
              out.switchTo(new FileWriter(f, false))
            case "reset" => out.switchTo(null)
            case other =>
              sys.error("Invalid file switch command: " + o)
          }
          ""
        case _ => super.toString(o, formatString, locale)
      }
    }
  }

  private class DefaultValueRenderer extends AttributeRenderer {
    def toString(o: scala.Any, formatString: String, locale: Locale): String =
      formatString match {
        case "Debug" =>
          ""
        case _ =>
          o.toString
      }
  }

  def process(addStParams: (ST) => Unit) = {
    templates foreach (st => {
      if (addStParams != null)
        addStParams(st)
      st.write(out.STWriter, context.session.locale)
      out.switchTo(null)
    })
  }

}


