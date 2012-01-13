package scoro.meta

import xml.Elem

class MetaObject[+D <: Entity](implicit aContext: Context[D]) extends Entity {
  var name: String = null;
  var caption = () => name;
  var sqlName = () => name;

  var xmlName = () => formatXmlName(name);
  var sourceName = () => name;

  def capitalizeFirstChar(s: String) =
    if (s.length <= 1) s.toUpperCase(context.locale) else s.charAt(0).toString.toUpperCase(context.locale) + s.substring(1)

  def decapitalizeFirstChar(s: String) =
    if (s.length <= 1) s.toLowerCase(context.locale) else s.charAt(0).toString.toLowerCase(context.locale) + s.substring(1)

  def formatSourceName(s: String) =
    decapitalizeFirstChar(s.split(" |_").reduceLeft({capitalizeFirstChar(_) + capitalizeFirstChar(_)}))

  def formatXmlName(s: String) =
    decapitalizeFirstChar(s.split(" |_").reduceLeft({capitalizeFirstChar(_) + capitalizeFirstChar(_)}))

  def formatSqlName(s: String) =
    s.replace(' ', '_').toUpperCase(context.locale);

  override def save() {
    throw new IllegalOperation("Trying to save meta object.");
  }

  override def load() {
    throw new IllegalOperation("Trying to load meta object.");
  }

  override def meta = null;

  override def validate() {};
  
}

