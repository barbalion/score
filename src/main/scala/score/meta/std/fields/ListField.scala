package score.meta.std.fields

import score.gen.meta.std.fields.ListFieldMeta
import score.meta._
import score.meta.fields.DynamicField
import score.xml._
import score.{Context, DetailContext}

import scala.xml.{Elem, Node}

class ListField[E <: Entity, T <: Entity](implicit context: Context)
  extends score.gen.meta.std.fields.ListField[E, T] with DynamicField[E, Seq[T]] with XmlSerializableToElement[E, Seq[T]] {

  override def compareValues(v1: Seq[T], v2: Seq[T]) = if (v1.sameElements(v2)) 0 else v1.hashCode() - v2.hashCode()

  override def meta = ListFieldMeta

  override def xmlName = MetaObject.capitalizeFirstChar(super.xmlName)

  def parseXmlElem(o: E, el: Elem) = {
    val detailContext: DetailContext = new DetailContext(o)
    el \ "_" map {
      case itemEl: Elem =>
        XmlStorage.createObjectFromXml(itemEl, detailContext) match {
          case item: T => item
          case _ => sys.error("Unexpected entity type in element: %s, %s" format(itemEl.label, itemEl.text))
        }
      case n: Node => sys.error("Error parsing XML. Element expected but \"%S\" got." format n.text)
    }
  }
}
