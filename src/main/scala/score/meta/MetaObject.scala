package score.meta

import score.Context
import score.xml.XmlSerializable

abstract class MetaObject(implicit context: Context) extends score.gen.meta.MetaObject with XmlSerializable {

  override def xmlName = if (raw_xmlName != null) raw_xmlName else MetaObject.formatXmlName(name)
}

object MetaObject {
  def capitalizeFirstChar(s: String)(implicit context: Context) = if (s == null) null
  else if (s.length <= 1) s.toUpperCase(context.session.locale) else s.charAt(0).toString.toUpperCase(context.session.locale) + s.substring(1)

  def decapitalizeFirstChar(s: String)(implicit context: Context) = if (s == null) null
  else if (s.length <= 1) s.toLowerCase(context.session.locale) else s.charAt(0).toString.toLowerCase(context.session.locale) + s.substring(1)

  def splitIndent(s: String)(implicit context: Context) = s.split("( |_|\\-|\\.|\\,|\\;)+|(?<=[a-z])(?=[A-Z])")

  //noinspection SpellCheckingInspection
  def escapeIdents(s: String): String = {
    s match {
      case "extends" => "extendz"
      case "class" => "clazz"
      case "package" => "packagge"
      case "case" => "caze"
      case "new" => "neww"
      case "def" => "deff"
      case "match" => "mattch"
      case "abstract" => "abztract"
      case other => other
    }
  }

  def formatSourceName(s: String)(implicit context: Context) = if (s == null) null
  else escapeIdents(capitalizeFirstChar(splitIndent(s).foldLeft("") { (a, b) => a + capitalizeFirstChar(b.toLowerCase(context.session.locale)) }))

  def formatXmlName(s: String)(implicit context: Context) = formatSourceName(s)

  def formatSqlName(s: String)(implicit context: Context) = if (s == null) null
  else splitIndent(s).reduceLeft(_.toUpperCase(context.session.locale) + "_" + _.toUpperCase(context.session.locale))
}
