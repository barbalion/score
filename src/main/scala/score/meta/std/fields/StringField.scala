package score.meta.std.fields

import score.Context
import score.meta._
import score.xml.XmlSerializableToAttribute

class StringField[E <: Entity](implicit context: Context) extends score.gen.meta.std.fields.StringField[E] with XmlSerializableToAttribute[E, String] {
  def parseXmlString(o: E, v: String) = v

}
