package score.meta

import xml.Elem

abstract class MetaObject(implicit context: Context) extends Entity with XmlSerializable {
  var name: String = null;

  var caption: String = name;

  var sourceName = () => formatSourceName(name);

  protected def formatSourceName(s: String) = MetaObject.formatSourceName(s)

  override def meta = null;

  override def validate() {};

  override def xmlName = MetaObject.formatXmlName(name);
}

trait XmlSerializable extends Entity {
  def xmlName: String = null;
}

trait DbSerializable extends Entity {
  var sqlName: String = null;// = MetaObject.formatSqlName(name);
}

object MetaObject {
  def capitalizeFirstChar(s: String)(implicit context: Context) = if (s == null) null else
    if (s.length <= 1) s.toUpperCase(context.session.locale) else s.charAt(0).toString.toUpperCase(context.session.locale) + s.substring(1)

  def decapitalizeFirstChar(s: String)(implicit context: Context) = if (s == null) null else
    if (s.length <= 1) s.toLowerCase(context.session.locale) else s.charAt(0).toString.toLowerCase(context.session.locale) + s.substring(1)

  protected def splitIndent(s: String)(implicit context: Context) =
    s.split(" |_|(?<=[a-z])(?=[A-Z])") map (_.toLowerCase(context.session.locale))

  protected def formatSourceName(s: String)(implicit context: Context) = if (s == null) null else
    capitalizeFirstChar(splitIndent(s).reduceLeft({capitalizeFirstChar(_) + capitalizeFirstChar(_)}))

  protected def formatXmlName(s: String)(implicit context: Context) = formatSourceName(s)

  protected def formatSqlName(s: String)(implicit context: Context) = if (s == null) null else
    splitIndent(s).reduceLeft(_.toUpperCase(context.session.locale) + "_" + _.toUpperCase(context.session.locale));

}

class MetaObjectMeta(implicit context: Context) extends EntityMeta[Entity] with AbstractEntityMeta[Entity] {
  properties =
    new StringField[EntityMeta[Entity]] {
      name = "Name"
      override def getFrom(o: EntityMeta[Entity]) = o.name
      override def setTo(o: EntityMeta[Entity], v: String) {o.name = v}
    } ::
    new StringField[EntityMeta[Entity]] {
      name = "Caption"
      override def getFrom(o: EntityMeta[Entity]) = o.caption
      override def setTo(o: EntityMeta[Entity], v: String) {o.caption = v}
    } ::
      properties map (_.asInstanceOf[EntityProperty[Entity, Any]])
}

object MetaObjectMeta extends MetaObjectMeta()(CompiledModelContext) {
  name = "Meta Object"
  CompiledModel.registerEntity(this)
}
