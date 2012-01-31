package score.meta

import xml.Elem
import util.parsing.json.JSONObject
import java.sql.ResultSet

abstract class EntityProperty[E <: Entity, T](implicit context: Context) extends MetaObject {
  var isKey = false;

  def compareIn(o1: E, o2: E): Int

  def equalsIn(o1: E, o2: E): Boolean
  
  def getFrom(o: E): T

  def isAssignedIn(o: E): Boolean

  def setTo(o: E, v: T)

  override def xmlName = MetaObject.decapitalizeFirstChar(super.xmlName);
}
      
trait DynamicProperty[E <: Entity, T] extends EntityProperty[E, T] {
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

trait XmlSerializableProperty[E <: Entity, T] extends EntityProperty[E, T] {
  def setFromXml(o: E, elem: Elem)
}

trait JsonSerializableProperty[E <: Entity, T] extends EntityProperty[E, T] {
  def parseFromJson(jo: JSONObject): T
}

trait DbSerializableProperty[E <: Entity, T] extends EntityProperty[E, T] {
  def parseFromDb(rs: ResultSet): T
}

trait SerializableProperty[E <: Entity, T] extends XmlSerializableProperty[E, T] with JsonSerializableProperty[E, T] with DbSerializableProperty[E, T] {
}

