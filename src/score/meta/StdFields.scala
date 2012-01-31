package score.meta

import util.parsing.json.JSONObject
import java.sql.ResultSet
import xml.{NodeSeq, Elem}

abstract class IntField[E <: Entity](implicit context: Context) extends SimpleValueField[E, Int] with XmlSerializableToAttribute[E, Int] {
  def compareValues(v1: Int, v2: Int) = v1.compareTo(v2)
  def sourceTypeName = "Int"
}

abstract class StringField[E <: Entity](implicit context: Context) extends SimpleComparableValueField[E, String] with XmlSerializableToAttribute[E, String] {
  def sourceTypeName = "String"

  def parseXmlString(v: String) = v

  def parseFromDb(rs: ResultSet) = null
  def parseFromJson(jo: JSONObject) = null

  def meta = StringFieldMeta
}

abstract class BooleanField[E <: Entity](implicit context: Context) extends SimpleValueField[E, Boolean] with XmlSerializableToAttribute[E, Boolean] {
  def compareValues(v1: Boolean, v2: Boolean) = v1.compareTo(v2)
  def sourceTypeName = "Boolean"
}

abstract class DoubleField[E <: Entity](implicit context: Context) extends SimpleValueField[E, Double] with XmlSerializableToAttribute[E, Double] {
  def compareValues(v1: Double, v2: Double) = v1.compareTo(v2)
  def sourceTypeName = "Double"
}

abstract class ListField[E <: Entity, I <: Entity](implicit context: Context)
  extends SimpleValueField[E, List[I]] with DynamicField[E, List[I]] with SerializableField[E, List[I]] with XmlSerializableToElement[E, List[I]]
{
  var of: EntityMeta[I] = null;

  def sourceTypeName = "List[Entity]"

  def compareValues(v1: List[I], v2: List[I]) = if (v1.sameElements(v2)) 0 else v1.hashCode() - v2.hashCode()

  def parseFromDb(rs: ResultSet) = null

  def parseFromJson(jo: JSONObject) = null

  def meta = ListFieldMeta

  override def xmlName = MetaObject.capitalizeFirstChar(super.xmlName)

  override def parseXmlElem(el: NodeSeq, objectContext: Context) = {
    (el \ "_" map (itemEl => {
      val o = XmlStorage.createObjectFromXml(itemEl.asInstanceOf[Elem], objectContext);
      if (o.isInstanceOf[I])
        o.asInstanceOf[I]
      else
        sys.error("Unexpected entity type in element: %s, %s" format (itemEl.label, itemEl.text));
    })).toList
  }
}

class StringFieldMeta(implicit context: Context) extends FieldMeta {

}
class ListFieldMeta(implicit context: Context) extends FieldMeta {

}
class IntegerFieldMeta(implicit context: Context) extends FieldMeta {
}

object StringFieldMeta extends StringFieldMeta()(CompiledModelContext) {
  name = "String"
  CompiledModel.registerEntity(this)

  override def newObject(implicit objectContext: Context) = new StringField[DynamicEntity]()(objectContext) {}
}

object ListFieldMeta extends ListFieldMeta()(CompiledModelContext) {
  name = "List"
  CompiledModel.registerEntity(this)
}

object IntegerFieldMeta extends IntegerFieldMeta()(CompiledModelContext) {
  name = "Int"
  CompiledModel.registerEntity(this)
}
