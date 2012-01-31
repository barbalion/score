package score.gen.meta.std.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class ListField[E <: Entity, T <: Entity](implicit context: Context) extends score.meta.fields.SimpleValueField[E, Seq[T]] {
  override def meta: score.meta.EntityMeta = score.gen.meta.std.fields.ListFieldMeta

  var raw_of: Ref[score.meta.EntityMeta] = null
  def of = raw_of
  def of_=(v: Ref[score.meta.EntityMeta]) {raw_of = v; setFieldState({state_of = _})}
  var state_of = Field.State.Init
}

abstract class ListFieldMeta(implicit context: Context) extends score.gen.meta.fields.SimpleValueFieldMeta with XmlSerializableEntityMeta {

  this.name = "List Field"
  this.hint = "Ancestor for all model meta objects in Score"
  this.xmlName = "List"
  this.module = "Score"
  this.packagge = "Meta Std Fields"
  this.extendz = score.gen.meta.fields.SimpleValueFieldMeta
  this.customized = true
  this.genConstructor = "ListFieldMetaConstructor"
  this.genClassParams = "ListFieldClassParams"
  this.genClassRefParams = "ListFieldClassRefParams"
  this.genClassExtendParams = "StdFieldClassExtendParams"
  this.isAbstract = false
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _of = new score.meta.std.fields.RefField[score.meta.std.fields.ListField[Entity, score.meta.Entity], score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "of"
      this.hint = "The name of referred entity."
      this.required = true
      this.to = score.gen.meta.EntityMetaMeta
    }

    override def getFrom(o: score.meta.std.fields.ListField[Entity, score.meta.Entity]) = o.of
    override def setTo(o: score.meta.std.fields.ListField[Entity, score.meta.Entity], v: Ref[score.meta.EntityMeta]) {o.of = v}
    override def getStateFrom(o: score.meta.std.fields.ListField[Entity, score.meta.Entity]) = o.state_of
    override def setStateIn(o: score.meta.std.fields.ListField[Entity, score.meta.Entity], state: Field.State.Value) = o.state_of = state
  }

  fields = fields ++ Seq(_of)

  this.genSourceType = "ListSourceType"
  this.genInitValue = "ListInitValue"
  this.genValue = "ListValue"
}

object ListFieldMeta extends score.gen.meta.std.fields.ListFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.meta.std.fields.ListField[Entity, score.meta.Entity] = new score.meta.std.fields.ListField[Entity, score.meta.Entity]()(objectContext)

  CompiledModel.registerEntity(this)
}
