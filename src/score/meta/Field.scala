package score.meta

import util.parsing.json.JSONObject
import java.sql.ResultSet
import xml.{NodeSeq, Elem}

abstract class Field[E <: Entity, T](implicit context: Context) extends MetaObject {
  var isKey = false;

  def compareIn(o1: E, o2: E): Int

  def equalsIn(o1: E, o2: E): Boolean
  
  def getFrom(o: E): T

  def isAssignedIn(o: E): Boolean

  def setTo(o: E, v: T)

  override def xmlName = MetaObject.decapitalizeFirstChar(super.xmlName);
}
      
trait DynamicField[E <: Entity, T] extends Field[E, T] {
  def getFrom(o: E): T = {
    o match {
      case o: DynamicEntity => o.applyDynamic(name).asInstanceOf[T]
      case _ => sys.error("Cannot get dynamic field from non-dynamic entity.")
    }
  }

  def setTo(o: E, v: T) {
    o match {
      case o: DynamicEntity => o.updateDynamic(name, v)
      case _ => sys.error("Cannot update dynamic field of non-dynamic entity.")
    }
  }
}

trait XmlSerializableField[E <: Entity, T] extends Field[E, T] {
  def setFromXml(o: E, el: Elem)
}

trait JsonSerializableField[E <: Entity, T] extends Field[E, T] {
  def parseFromJson(jo: JSONObject): T
}

trait DbSerializableField[E <: Entity, T] extends Field[E, T] {
  def parseFromDb(rs: ResultSet): T
}

trait SerializableField[E <: Entity, T] extends XmlSerializableField[E, T] with JsonSerializableField[E, T] with DbSerializableField[E, T] {
}

trait XmlSerializableToAttribute[E <: Entity, T] extends Field[E, T] with XmlSerializableField[E, T] {

  override def setFromXml(o: E, el: Elem) {
    val node = el \ ("@" + xmlName)
    if (!node.isEmpty)
      setTo(o, parseXmlString(node.text));
  }

  def parseXmlString(v: String): T;
}

trait XmlSerializableToElement[E <: Entity, T] extends Field[E, T] with XmlSerializableField[E, T] {

  override def setFromXml(o: E, el: Elem) {
    val node = el \ xmlName
    if (!node.isEmpty)
      setTo(o, parseXmlElem(node, o.context));
  }

  def parseXmlElem(el: NodeSeq, objectContext: Context): T;
}

abstract class SimpleValueField[E <: Entity, T](implicit context: Context) extends Field[E, T] with DynamicField[E, T] {
  override def compareIn(o1: E, o2: E) = compareWithNulls(getFrom(o1), getFrom(o2))
  override def equalsIn(o1: E, o2: E) = compareIn(o1, o2) == 0
  protected def compareWithNulls(v1: T, v2: T) = if (v1 == null) if (v2 == null) 0 else -1 else if (v2 == null) 1 else compareValues(v1, v2)
  def compareValues(v1: T, v2: T): Int

  def isAssignedIn(o: E) = getFrom(o) != null
}

abstract class SimpleComparableValueField[E <: Entity, T <: java.lang.Comparable[T]](implicit context: Context) extends SimpleValueField[E, T] {
  override def compareValues(v1: T, v2: T) = v1.compareTo(v2)
}

class FieldMeta(implicit context: Context) extends MetaObjectMeta {

}

object FieldMeta extends FieldMeta()(CompiledModelContext) {}