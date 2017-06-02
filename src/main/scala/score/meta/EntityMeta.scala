package score.meta

import score.Context
import score.meta.fields.{Field, SimpleValueField}
import score.meta.model._

class EntityMeta(implicit context: Context) extends score.gen.meta.EntityMeta {
  //noinspection LanguageFeature
  implicit protected def FieldConvert(f: Field[_, _]): Field[Entity, Any] = f.asInstanceOf[Field[Entity, Any]]

  //noinspection LanguageFeature
  implicit protected def FieldSeqConvert(x: Seq[Field[_, _]]): Seq[Field[Entity, Any]] = x.asInstanceOf[Seq[Field[Entity, Any]]]

  override def init(): Unit = {
    super.init()
    fields foreach {
      _.init()
    }
  }

  def newObject(implicit objectContext: Context): Entity = new DynamicEntity(this)(objectContext)

  def key = fields filter (_.isKey) match {
    case Nil => BadMetaModel(this, "Entity without a key!")
    case k => k
  }

  def isKeyEqual(e1: Entity, e2: Entity): Boolean = key forall (_.equalsIn(e1, e2))

  final def is(meta: EntityMeta): Boolean = this.isInstanceOf[meta.type] || meta.name == name || (extendz != null && extendz.is(meta))

  def keyCondition(keyObject: Entity) = {
    key map {
      f => new Condition.Equals(f.asInstanceOf[SimpleValueField[Entity, Any]], f.getFrom(keyObject))
    } match {
      case c :: Nil => c
      case head :: tail => tail.toSeq.foldLeft(head.asInstanceOf[Condition]) {
        (a: Condition, b: Condition) => new Condition.And(a, b)
      }
    }
  }

  def fieldByName(fieldName: String) = {
    fields.find(_.name == fieldName).orElse(sys.error("Field %s not found in entity %s" format(fieldName, name))).get
  }

}

