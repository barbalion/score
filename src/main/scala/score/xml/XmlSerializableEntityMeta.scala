package score.xml

import score.Context
import score.meta.{Condition, Entity, EntityMeta}

import scala.util.{Success, Failure, Try}
import scala.xml.Elem

trait XmlSerializableEntityMeta extends score.meta.EntityMeta {
  def newObjectFromXml(el: Elem, objectContext: Context) = {
    val o = newObject(objectContext)
    XmlSerializable.serializableFields(fields) foreach {_.setFromXml(o, el, "")}
    o
  }

  def keyCondition(el: Elem, prefix: String, objectContext: Context): Option[Condition] = {
    val o = newObject(objectContext)
    XmlSerializable.serializableFields(key) foreach {
      _.setFromXml(o, el, prefix)
    }
    if (o.isKeyAssigned) {
      Some(super.keyCondition(o))
    } else
      None
  }
}
