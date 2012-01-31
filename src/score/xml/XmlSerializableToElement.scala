package score.xml

import score.meta.fields.Field

import xml.Elem
import score.meta._

trait XmlSerializableToElement[E <: Entity, T] extends Field[E, T] with XmlSerializableField[E, T] {

  override def setFromXml(o: E, el: Elem, prefix: String) {
    val node = el \ (if (prefix.equals("")) xmlName else prefix)
    if (!node.isEmpty)
      setTo(o, parseXmlElem(o, node.head.asInstanceOf[Elem]))
  }

  def parseXmlElem(o: E, el: Elem): T
}

