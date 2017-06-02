package score.gen.meta.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

abstract class Field[E <: Entity, T](implicit context: Context) extends score.meta.MetaObject {
  override def meta: score.meta.EntityMeta = score.gen.meta.fields.FieldMeta

  var raw_genValue: String = "InlineValue"
  def genValue = raw_genValue
  def genValue_=(v: String) {raw_genValue = v; setFieldState({state_genValue = _})}
  var state_genValue = Field.State.Init

  var raw_isKey: Boolean = false
  def isKey = raw_isKey
  def isKey_=(v: Boolean) {raw_isKey = v; setFieldState({state_isKey = _})}
  var state_isKey = Field.State.Init

  var raw_readOnly: Boolean = false
  def readOnly = raw_readOnly
  def readOnly_=(v: Boolean) {raw_readOnly = v; setFieldState({state_readOnly = _})}
  var state_readOnly = Field.State.Init

  var raw_required: Boolean = false
  def required = raw_required
  def required_=(v: Boolean) {raw_required = v; setFieldState({state_required = _})}
  var state_required = Field.State.Init
}

abstract class FieldMeta(implicit context: Context) extends score.gen.meta.FieldMeta with XmlSerializableEntityMeta {

  this.name = "Field"
  this.hint = "Score Field Object."
  this.xmlName = "FieldStd"
  this.module = "Score"
  this.packagge = "Meta Fields"
  this.extendz = score.gen.meta.MetaObjectMeta
  this.customized = true
  this.customMeta = score.gen.meta.FieldMetaMeta
  this.genConstructor = "FieldMetaConstructor"
  this.genClassParams = "FieldClassParams"
  this.genClassRefParams = "FieldClassRefParams"
  this.genClassExtendParams = "FieldClassExtendParams"
  this.isAbstract = true
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _genValue = new score.meta.std.fields.StringField[score.meta.fields.Field[Entity, Any]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Value"
      this.hint = ""
      this.defaultValue = "InlineValue"
    }

    override def getFrom(o: score.meta.fields.Field[Entity, Any]) = o.genValue
    override def setTo(o: score.meta.fields.Field[Entity, Any], v: String) {o.genValue = v}
    override def getStateFrom(o: score.meta.fields.Field[Entity, Any]) = o.state_genValue
    override def setStateIn(o: score.meta.fields.Field[Entity, Any], state: Field.State.Value) = o.state_genValue = state
  }

  val _isKey = new score.meta.std.fields.BooleanField[score.meta.fields.Field[Entity, Any]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Is Key"
      this.hint = "The field is a part of primary key."
    }

    override def getFrom(o: score.meta.fields.Field[Entity, Any]) = o.isKey
    override def setTo(o: score.meta.fields.Field[Entity, Any], v: Boolean) {o.isKey = v}
    override def getStateFrom(o: score.meta.fields.Field[Entity, Any]) = o.state_isKey
    override def setStateIn(o: score.meta.fields.Field[Entity, Any], state: Field.State.Value) = o.state_isKey = state
  }

  val _readOnly = new score.meta.std.fields.BooleanField[score.meta.fields.Field[Entity, Any]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Read-Only"
      this.hint = "Defines the ability to set the field value."
    }

    override def getFrom(o: score.meta.fields.Field[Entity, Any]) = o.readOnly
    override def setTo(o: score.meta.fields.Field[Entity, Any], v: Boolean) {o.readOnly = v}
    override def getStateFrom(o: score.meta.fields.Field[Entity, Any]) = o.state_readOnly
    override def setStateIn(o: score.meta.fields.Field[Entity, Any], state: Field.State.Value) = o.state_readOnly = state
  }

  val _required = new score.meta.std.fields.BooleanField[score.meta.fields.Field[Entity, Any]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Required"
      this.hint = "The field must have non-empty value for the object to validated."
    }

    override def getFrom(o: score.meta.fields.Field[Entity, Any]) = o.required
    override def setTo(o: score.meta.fields.Field[Entity, Any], v: Boolean) {o.required = v}
    override def getStateFrom(o: score.meta.fields.Field[Entity, Any]) = o.state_required
    override def setStateIn(o: score.meta.fields.Field[Entity, Any], state: Field.State.Value) = o.state_required = state
  }

  fields = fields ++ Seq(_genValue, _isKey, _readOnly, _required)

}

object FieldMeta extends score.gen.meta.fields.FieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context) = throw new IllegalOperation("Cannot instantiate abstract entity.")

  CompiledModel.registerEntity(this)
}
