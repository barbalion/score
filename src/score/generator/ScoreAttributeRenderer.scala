package score.generator

import java.util.Locale

import org.stringtemplate.v4._
import score.Context
import score.meta._

class ScoreAttributeRenderer(implicit val context: Context) extends AttributeRenderer {
  def toString(o: AnyRef, formatString: String, locale: Locale) = {
    var s: String = o.toString
    formatString match {
      case null => s
      case _ => formatString.toLowerCase(context.session.locale).split("[\\s\\,\\;]+").foldLeft(s) {
        (s, formatToken: String) =>
          formatToken match {
            case "upper" => s.toUpperCase(context.session.locale)
            case "lower" => s.toLowerCase(context.session.locale)
            case "upperfirst" => MetaObject.capitalizeFirstChar(s)
            case "lowerfirst" => MetaObject.decapitalizeFirstChar(s)
            case "escapecstring" => s.replaceAllLiterally("\\", "\\\\").replaceAllLiterally("\"", "\\\"").replaceAllLiterally("\n", "\\\n").replaceAllLiterally("\r", "\\\r").replaceAllLiterally("\t", "\\\t")
            case "escapesourceidents" => MetaObject.escapeIdents(s)
            case other => sys.error("Unknown format: \"%s\"." format other)
          }
      }
    }
  }

}
