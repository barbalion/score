package score.xml

import score.meta.fields.Field

import xml.Elem
import score.meta._

trait XmlSerializableField[E <: Entity, T] extends Field[E, T] {
  def setFromXml(o: E, el: Elem, prefix: String)
}

