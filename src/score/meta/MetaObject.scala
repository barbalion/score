package scoro.meta

import xml.Elem

class MetaObject(implicit context: Context[MetaObject]) extends Entity {
  var name: String = null;
  var caption: String = name;
  var sqlName = () => name;

  var xmlName = () => formatXmlName(name);
  var sourceName = () => formatSourceName(name);

  def capitalizeFirstChar(s: String) =
    if (s.length <= 1) s.toUpperCase(context.session.locale) else s.charAt(0).toString.toUpperCase(context.session.locale) + s.substring(1)

  def decapitalizeFirstChar(s: String) =
    if (s.length <= 1) s.toLowerCase(context.session.locale) else s.charAt(0).toString.toLowerCase(context.session.locale) + s.substring(1)

  protected def splitIndent(s: String) = s.split(" |_|(?<[a-z])(?=[A-Z])") map (_.toLowerCase(context.session.locale))
  
  protected def formatSourceName(s: String) =
    decapitalizeFirstChar(splitIndent(s).reduceLeft({capitalizeFirstChar(_) + capitalizeFirstChar(_)}))

  protected def formatXmlName(s: String) =
    decapitalizeFirstChar(splitIndent(s).reduceLeft({capitalizeFirstChar(_) + capitalizeFirstChar(_)}))

  protected def formatSqlName(s: String) =
    splitIndent(s).reduceLeft(_.toUpperCase(context.session.locale) + "_" + _.toUpperCase(context.session.locale));

  override def meta = null;

  override def validate() {};
  
}

