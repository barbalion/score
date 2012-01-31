package score.xml

import score.meta._
import score.meta.fields.Field

import scala.xml.Elem

trait XmlSerializableToAttribute[E <: Entity, T] extends Field[E, T] with XmlSerializableField[E, T] {

  override def setFromXml(o: E, el: Elem, prefix: String) {
    val attName = if (!prefix.equals("") && isKey && master.asInstanceOf[EntityMeta].key.tail == Nil) prefix else xmlName
    el.attribute(attName) map {a =>
      if (!a.isEmpty)
        setTo(o, parseXmlString(o, a.text))
    }
  }

  def parseXmlString(o: E, v: String): T
}

