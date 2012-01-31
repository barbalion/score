package score.meta

import xml.Elem

abstract class EntityMeta[E <: Entity](implicit context: Context) extends MetaObject {

  var fields: List[Field[Entity, Any]] = Nil;

  def newObject(implicit objectContext: Context): E;

  def key = fields filter (_.isKey)

  def fieldByName(fieldName: String) = {
    fields.find(_.name == fieldName).orElse(sys.error("Field %s not found in entity %s" format (fieldName, name))).get
  }

  def meta = EntityMetaMeta
}

trait AbstractEntityMeta[E <: Entity] extends EntityMeta[E] {
  override def newObject(implicit objectContext: Context): E = {sys.error("Operation not supported for abstract class")}
}


class EntityMetaMeta(implicit context: Context) extends MetaObjectMeta {
  fields =
    new ListField[EntityMeta[Entity], Field[Entity,  Any]] {
      name = "Fields"
//      of =
      override def getFrom(o: EntityMeta[Entity]) = o.fields
      override def setTo(o: EntityMeta[Entity], v: List[Field[Entity, Any]]) {o.fields = v}
    } ::
      fields map (_.asInstanceOf[Field[Entity, Any]])

  override def newObject(implicit objectContext: Context) = new EntityMeta[Entity]()(objectContext) {
    def newObject(implicit objectContext: Context) = new DynamicEntity(this)(objectContext)
  };
}

object EntityMetaMeta extends EntityMetaMeta()(CompiledModelContext) {
  name = "Entity Meta"
  override def xmlName = "Entity"

  CompiledModel.registerEntity(this)

}

trait XmlSerializableEntityMeta[E <: Entity] extends EntityMeta[E] {
  def newObjectFromXml(el: Elem, objectContext: Context) = {
    val o = newObject(objectContext)
    fields map (p => {
      if (p.isInstanceOf[XmlSerializableField[Entity, Any]])
        p.asInstanceOf[XmlSerializableField[Entity, Any]].setFromXml(o, el)
      else
        sys.error("Field does not support xml serialization: %s" format (p.toString))
    })
    o
  }

}