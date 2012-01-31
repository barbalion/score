package score.meta

import xml.Elem
import util.parsing.json.JSONObject
import java.sql.ResultSet

abstract class IntField[E <: Entity](implicit context: Context) extends SimpleValueField[E, Int] {
  def compareValues(v1: Int, v2: Int) = v1.compareTo(v2)
  def sourceTypeName = "Int"
}

abstract class StringField[E <: Entity](implicit context: Context) extends SimpleComparableValueField[E, String] {
  def sourceTypeName = "String"

  def parseXmlString(v: String) = v

  def parseFromDb(rs: ResultSet) = null
  def parseFromJson(jo: JSONObject) = null
}

abstract class BooleanField[E <: Entity](implicit context: Context) extends SimpleValueField[E, Boolean] {
  def compareValues(v1: Boolean, v2: Boolean) = v1.compareTo(v2)
  def sourceTypeName = "Boolean"
}

abstract class DoubleField[E <: Entity](implicit context: Context) extends SimpleValueField[E, Double] {
  def compareValues(v1: Double, v2: Double) = v1.compareTo(v2)
  def sourceTypeName = "Double"
}

abstract class ListField[E <: Entity, I <: Entity](implicit context: Context) extends SimpleValueField[E, Seq[I]] {
  var of: EntityMeta[I];

  def sourceTypeName = "Seq[Entity]"

  def compareValues(v1: Seq[I], v2: Seq[I]) = if (v1.sameElements(v2)) 0 else v1.hashCode() - v2.hashCode()

  def parseFromDb(rs: ResultSet) = null

  def parseFromJson(jo: JSONObject) = null

  override def setFromXml(o: E, elem: Elem) {
    val node = (elem \ name \ "_")
    if (!node.isEmpty) {
      setTo(o,
        node map (el => {
          val st = context.storage.asInstanceOf[XmlStorage];
          val o = st.createEntityFromElem(el.asInstanceOf[Elem]);
          if (o.isInstanceOf[I]) o.asInstanceOf[I] else sys.error("Unexpected entity type in element: %s, %s" format (elem.label, elem.text));
        })
      )
    }
  }
}

class StringFieldMeta(implicit context: Context) extends MetaObjectMeta {

}
class IntegerFieldMeta(implicit context: Context) extends MetaObjectMeta {
}

object StringFieldMeta extends StringFieldMeta()(CompiledModelContext) {
  name = "String"
  CompiledModel.registerEntity(this)
}

object IntegerFieldMeta extends IntegerFieldMeta()(CompiledModelContext) {
  name = "Int"
  CompiledModel.registerEntity(this)
}
