package score.meta.std.fields

import score.Context
import score.meta._
import score.xml.XmlSerializableToAttribute

import scala.util.{Failure, Success, Try}

class IntegerField[E <: Entity](implicit context: Context) extends score.gen.meta.std.fields.IntegerField[E] with XmlSerializableToAttribute[E, Integer] {
  override def parseXmlString(o: E, v: String): Integer = v match {
    case "inf" => Integer.MAX_VALUE
    case s => Try({
      s.toInt
    }) match {
      case Success(i) => i
      case Failure(e) => sys.error("\"%s\" is not a number" format s)
    }
  }
}
