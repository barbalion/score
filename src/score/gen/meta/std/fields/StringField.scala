package score.gen.meta.std.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class StringField[E <: Entity](implicit context: Context) extends score.meta.fields.ComparableSimpleValueField[E, String] {
  override def meta: score.meta.EntityMeta = score.gen.meta.std.fields.StringFieldMeta

  var raw_editMask: String = null
  def editMask = raw_editMask
  def editMask_=(v: String) {raw_editMask = v; setFieldState({state_editMask = _})}
  var state_editMask = Field.State.Init

  var raw_defaultValue: String = null
  def defaultValue = raw_defaultValue
  def defaultValue_=(v: String) {raw_defaultValue = v; setFieldState({state_defaultValue = _})}
  var state_defaultValue = Field.State.Init

  var raw_test: Ref[score.meta.EntityMeta] = null
  def test = raw_test
  def test_=(v: Ref[score.meta.EntityMeta]) {raw_test = v; setFieldState({state_test = _})}
  var state_test = Field.State.Init
}

abstract class StringFieldMeta(implicit context: Context) extends score.gen.meta.fields.ComparableSimpleValueFieldMeta with XmlSerializableEntityMeta {

  this.name = "String Field"
  this.hint = "Ancestor for all model meta objects in Score"
  this.xmlName = "String"
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

  val _editMask = new score.meta.std.fields.StringField[score.meta.std.fields.StringField[Entity]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Edit Mask"
      this.hint = "A mask for an editor."
    }

    override def getFrom(o: score.meta.std.fields.StringField[Entity]) = o.editMask
    override def setTo(o: score.meta.std.fields.StringField[Entity], v: String) {o.editMask = v}
    override def getStateFrom(o: score.meta.std.fields.StringField[Entity]) = o.state_editMask
    override def setStateIn(o: score.meta.std.fields.StringField[Entity], state: Field.State.Value) = o.state_editMask = state
  }

  val _defaultValue = new score.meta.std.fields.StringField[score.meta.std.fields.StringField[Entity]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Default Value"
      this.hint = "Default value of the field"
    }

    override def getFrom(o: score.meta.std.fields.StringField[Entity]) = o.defaultValue
    override def setTo(o: score.meta.std.fields.StringField[Entity], v: String) {o.defaultValue = v}
    override def getStateFrom(o: score.meta.std.fields.StringField[Entity]) = o.state_defaultValue
    override def setStateIn(o: score.meta.std.fields.StringField[Entity], state: Field.State.Value) = o.state_defaultValue = state
  }

  val _test = new score.meta.std.fields.RefField[score.meta.std.fields.StringField[Entity], score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Test"
      this.to = score.gen.meta.EntityMetaMeta
    }

    override def getFrom(o: score.meta.std.fields.StringField[Entity]) = o.test
    override def setTo(o: score.meta.std.fields.StringField[Entity], v: Ref[score.meta.EntityMeta]) {o.test = v}
    override def getStateFrom(o: score.meta.std.fields.StringField[Entity]) = o.state_test
    override def setStateIn(o: score.meta.std.fields.StringField[Entity], state: Field.State.Value) = o.state_test = state
  }

  fields = fields ++ Seq(_editMask, _defaultValue, _test)

  this.genSourceType = "StringSourceType"
  this.genValue = "StringValue"
}

object StringFieldMeta extends score.gen.meta.std.fields.StringFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.meta.std.fields.StringField[Entity] = new score.meta.std.fields.StringField[Entity]()(objectContext)

  CompiledModel.registerEntity(this)
}
