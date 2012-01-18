package scoro.meta

import xml.Elem

abstract class EntityMeta[E <: Entity](implicit context: Context[EntityMeta[E]]) extends MetaObject {
  private var fProps: List[EntityProperty[E, Any]] = Nil;

  def properties = fProps;

  protected def properties_=(aProps: List[EntityProperty[E, Any]]) {fProps = aProps}

  def newObject(implicit objectContext: Context[E]): E;

  def newObjectFromXml(el: Elem)(implicit objectContext: Context[E]) = {
    val o = newObject

    o
  }

  def key = properties filter (_.isKey)

  def fieldByName(fieldName: String) = {
    properties.find(_.name == fieldName).orElse(sys.error("Property %s not found in entity %s" format (fieldName, name))).get
  }
}

class RunTimeEntityMetaMeta(implicit context: Context[Nothing]) extends EntityMeta[EntityMeta[Nothing]] {
  def newObject(implicit objectContext: Context[EntityMeta[Nothing]]) = new RunTimeEntityMetaMeta() {
    def newObject(implicit objectContext: Context[Nothing]) = {sys.error("Unsupported")}
  }.asInstanceOf[EntityMeta[Nothing]]

  name = "Entity Meta"
  properties =
    new StringField[EntityMeta[Nothing]] {
      name = "Name"
      def getFrom(o: EntityMeta[Nothing]) = o.name
      def setTo(o: EntityMeta[Nothing], v: String) {o.name = v}
    } ::
    new StringField[EntityMeta[Nothing]] {
      name = "Caption"
      def getFrom(o: EntityMeta[Nothing]) = o.caption
      def setTo(o: EntityMeta[Nothing], v: String) {o.caption = v}
    } ::
      Nil map (_.asInstanceOf[EntityProperty[EntityMeta[Nothing], Any]])

}


object EntityMetaMeta extends RunTimeEntityMetaMeta()(RunTimeModelContext) {
  RunTimeModel.registerEntity(this)
}