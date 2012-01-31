package score.meta.std.fields

import score.Context
import score.meta._
import score.xml.XmlSerializableToAttribute

class BooleanField[E <: Entity](implicit context: Context) extends score.gen.meta.std.fields.BooleanField[E] with XmlSerializableToAttribute[E, Boolean] {

  override def parseXmlString(o: E, v: String) = v.toLowerCase match {
    case "false" => false
    case "true" => true
    case "no" => false
    case "yes" => true
    case "off" => false
    case "on" => true
    case "0" => false
    case "1" => true
    case s => sys.error("Invalid xml value for boolean field <%s %s=\"%s\"/>" format (master.xmlName, xmlName, s))
  }

  override def compareValues(v1: Boolean, v2: Boolean) = v1.compareTo(v2)
}
