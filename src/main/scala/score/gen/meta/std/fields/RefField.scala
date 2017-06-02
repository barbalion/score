package score.gen.meta.std.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class RefField[E <: Entity, T <: score.meta.Entity](implicit context: Context) extends score.meta.fields.SimpleValueField[E, Ref[T]] {
  override def meta: score.meta.EntityMeta = score.gen.meta.std.fields.RefFieldMeta

  var raw_to: Ref[score.meta.EntityMeta] = null
  def to = raw_to
  def to_=(v: Ref[score.meta.EntityMeta]) {raw_to = v; setFieldState({state_to = _})}
  var state_to = Field.State.Init
}

abstract class RefFieldMeta(implicit context: Context) extends score.gen.meta.fields.SimpleValueFieldMeta with XmlSerializableEntityMeta {

  this.name = "Ref Field"
  this.hint = "Ancestor for all model meta objects in Score"
  this.xmlName = "Ref"
  this.module = "Score"
  this.packagge = "Meta Std Fields"
  this.extendz = score.gen.meta.fields.SimpleValueFieldMeta
  this.customized = true
  this.genConstructor = "RefFieldMetaConstructor"
  this.genClassParams = "RefFieldClassParams"
  this.genClassRefParams = "RefFieldClassRefParams"
  this.genClassExtendParams = "StdFieldClassExtendParams"
  this.isAbstract = false
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _to = new score.meta.std.fields.RefField[score.meta.std.fields.RefField[Entity, score.meta.EntityMeta], score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "to"
      this.hint = "The name of referred entity."
      this.required = true
      this.to = score.gen.meta.EntityMetaMeta
    }

    override def getFrom(o: score.meta.std.fields.RefField[Entity, score.meta.EntityMeta]) = o.to
    override def setTo(o: score.meta.std.fields.RefField[Entity, score.meta.EntityMeta], v: Ref[score.meta.EntityMeta]) {o.to = v}
    override def getStateFrom(o: score.meta.std.fields.RefField[Entity, score.meta.EntityMeta]) = o.state_to
    override def setStateIn(o: score.meta.std.fields.RefField[Entity, score.meta.EntityMeta], state: Field.State.Value) = o.state_to = state
  }

  fields = fields ++ Seq(_to)

  this.genSourceType = "RefSourceType"
  this.genValue = "RefValue"
}

object RefFieldMeta extends score.gen.meta.std.fields.RefFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.meta.std.fields.RefField[Entity, score.meta.EntityMeta] = new score.meta.std.fields.RefField[Entity, score.meta.EntityMeta]()(objectContext)

  CompiledModel.registerEntity(this)
}
