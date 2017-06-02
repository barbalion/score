package score.gen.meta.std.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class IntegerField[E <: Entity](implicit context: Context) extends score.meta.fields.ComparableSimpleValueField[E, Integer] {
  override def meta: score.meta.EntityMeta = score.gen.meta.std.fields.IntegerFieldMeta

  var raw_max: Integer = 0
  def max = raw_max
  def max_=(v: Integer) {raw_max = v; setFieldState({state_max = _})}
  var state_max = Field.State.Init

  var raw_min: Integer = 0
  def min = raw_min
  def min_=(v: Integer) {raw_min = v; setFieldState({state_min = _})}
  var state_min = Field.State.Init

  var raw_editMask: String = null
  def editMask = raw_editMask
  def editMask_=(v: String) {raw_editMask = v; setFieldState({state_editMask = _})}
  var state_editMask = Field.State.Init

  var raw_defaultValue: Integer = 0
  def defaultValue = raw_defaultValue
  def defaultValue_=(v: Integer) {raw_defaultValue = v; setFieldState({state_defaultValue = _})}
  var state_defaultValue = Field.State.Init
}

abstract class IntegerFieldMeta(implicit context: Context) extends score.gen.meta.fields.ComparableSimpleValueFieldMeta with XmlSerializableEntityMeta {

  this.name = "Integer Field"
  this.hint = "Ancestor for all model meta objects in Score"
  this.xmlName = "Int"
  this.module = "Score"
  this.packagge = "Meta Std Fields"
  this.extendz = score.gen.meta.fields.ComparableSimpleValueFieldMeta
  this.customized = true
  this.genClassParams = "StdFieldClassParams"
  this.genClassRefParams = "StdFieldClassRefParams"
  this.genClassExtendParams = "StdFieldClassExtendParams"
  this.isAbstract = false
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _max = new score.meta.std.fields.IntegerField[score.meta.std.fields.IntegerField[Entity]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Max"
      this.hint = "A maximum allowed value of the field. Zero means no limit."
    }

    override def getFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.max
    override def setTo(o: score.meta.std.fields.IntegerField[Entity], v: Integer) {o.max = v}
    override def getStateFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.state_max
    override def setStateIn(o: score.meta.std.fields.IntegerField[Entity], state: Field.State.Value) = o.state_max = state
  }

  val _min = new score.meta.std.fields.IntegerField[score.meta.std.fields.IntegerField[Entity]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Min"
      this.hint = "A minimum allowed value of the field. Zero means no limit."
    }

    override def getFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.min
    override def setTo(o: score.meta.std.fields.IntegerField[Entity], v: Integer) {o.min = v}
    override def getStateFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.state_min
    override def setStateIn(o: score.meta.std.fields.IntegerField[Entity], state: Field.State.Value) = o.state_min = state
  }

  val _editMask = new score.meta.std.fields.StringField[score.meta.std.fields.IntegerField[Entity]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Edit Mask"
      this.hint = "A mask for an editor."
    }

    override def getFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.editMask
    override def setTo(o: score.meta.std.fields.IntegerField[Entity], v: String) {o.editMask = v}
    override def getStateFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.state_editMask
    override def setStateIn(o: score.meta.std.fields.IntegerField[Entity], state: Field.State.Value) = o.state_editMask = state
  }

  val _defaultValue = new score.meta.std.fields.IntegerField[score.meta.std.fields.IntegerField[Entity]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Default Value"
      this.hint = "Default value of the field"
    }

    override def getFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.defaultValue
    override def setTo(o: score.meta.std.fields.IntegerField[Entity], v: Integer) {o.defaultValue = v}
    override def getStateFrom(o: score.meta.std.fields.IntegerField[Entity]) = o.state_defaultValue
    override def setStateIn(o: score.meta.std.fields.IntegerField[Entity], state: Field.State.Value) = o.state_defaultValue = state
  }

  fields = fields ++ Seq(_max, _min, _editMask, _defaultValue)

  this.genSourceType = "IntSourceType"
  this.genInitValue = "NumericInitValue"
}

object IntegerFieldMeta extends score.gen.meta.std.fields.IntegerFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.meta.std.fields.IntegerField[Entity] = new score.meta.std.fields.IntegerField[Entity]()(objectContext)

  CompiledModel.registerEntity(this)
}
