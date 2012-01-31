package score.meta

abstract class EntityMeta[E <: Entity](implicit context: Context) extends MetaObject {

  var properties: List[EntityProperty[Entity, Any]] = Nil;

  def newObject(implicit objectContext: Context): E;

  def key = properties filter (_.isKey)

  def fieldByName(fieldName: String) = {
    properties.find(_.name == fieldName).orElse(sys.error("Property %s not found in entity %s" format (fieldName, name))).get
  }
}

trait AbstractEntityMeta[E <: Entity] extends EntityMeta[E] {
  override def newObject(implicit objectContext: Context): E = {sys.error("Operation not supported for abstract class")}
}


class EntityMetaMeta(implicit context: Context) extends MetaObjectMeta {
  override def newObject(implicit objectContext: Context) = new EntityMeta[Entity]()(objectContext) {
    def newObject(implicit objectContext: Context) = new RunTimeEntity(this)(objectContext)
  };
}

object EntityMetaMeta extends EntityMetaMeta()(CompiledModelContext) {
  name = "Entity Meta"
  override def xmlName = "Entity"

  CompiledModel.registerEntity(this)

}