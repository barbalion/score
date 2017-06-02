package score.gen.meta

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class FieldMeta(implicit context: Context) extends score.gen.meta.MetaObjectMeta {
  override def meta: score.meta.EntityMeta = score.gen.meta.FieldMetaMeta

  var raw_genSourceType: String = null
  def genSourceType = raw_genSourceType
  def genSourceType_=(v: String) {raw_genSourceType = v; setFieldState({state_genSourceType = _})}
  var state_genSourceType = Field.State.Init

  var raw_genDef: String = "FieldDef"
  def genDef = raw_genDef
  def genDef_=(v: String) {raw_genDef = v; setFieldState({state_genDef = _})}
  var state_genDef = Field.State.Init

  var raw_genMetaDef: String = "FieldMeta"
  def genMetaDef = raw_genMetaDef
  def genMetaDef_=(v: String) {raw_genMetaDef = v; setFieldState({state_genMetaDef = _})}
  var state_genMetaDef = Field.State.Init

  var raw_genInitValue: String = "FieldInitValue"
  def genInitValue = raw_genInitValue
  def genInitValue_=(v: String) {raw_genInitValue = v; setFieldState({state_genInitValue = _})}
  var state_genInitValue = Field.State.Init

  var raw_genValue: String = "InlineValue"
  def genValue = raw_genValue
  def genValue_=(v: String) {raw_genValue = v; setFieldState({state_genValue = _})}
  var state_genValue = Field.State.Init
}

abstract class FieldMetaMeta(implicit context: Context) extends score.gen.meta.EntityMetaMeta with XmlSerializableEntityMeta {

  this.name = "Field Meta"
  this.hint = "Score Field Meta Object"
  this.xmlName = "Field"
  this.module = "Score"
  this.packagge = "Meta"
  this.extendz = score.gen.meta.EntityMetaMeta
  this.customMetaFor = score.gen.meta.fields.FieldMeta
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _genSourceType = new score.meta.std.fields.StringField[score.gen.meta.FieldMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Source Type"
      this.hint = ""
    }

    override def getFrom(o: score.gen.meta.FieldMeta) = o.genSourceType
    override def setTo(o: score.gen.meta.FieldMeta, v: String) {o.genSourceType = v}
    override def getStateFrom(o: score.gen.meta.FieldMeta) = o.state_genSourceType
    override def setStateIn(o: score.gen.meta.FieldMeta, state: Field.State.Value) = o.state_genSourceType = state
  }

  val _genDef = new score.meta.std.fields.StringField[score.gen.meta.FieldMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Def"
      this.hint = ""
      this.defaultValue = "FieldDef"
    }

    override def getFrom(o: score.gen.meta.FieldMeta) = o.genDef
    override def setTo(o: score.gen.meta.FieldMeta, v: String) {o.genDef = v}
    override def getStateFrom(o: score.gen.meta.FieldMeta) = o.state_genDef
    override def setStateIn(o: score.gen.meta.FieldMeta, state: Field.State.Value) = o.state_genDef = state
  }

  val _genMetaDef = new score.meta.std.fields.StringField[score.gen.meta.FieldMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Meta Def"
      this.hint = ""
      this.defaultValue = "FieldMeta"
    }

    override def getFrom(o: score.gen.meta.FieldMeta) = o.genMetaDef
    override def setTo(o: score.gen.meta.FieldMeta, v: String) {o.genMetaDef = v}
    override def getStateFrom(o: score.gen.meta.FieldMeta) = o.state_genMetaDef
    override def setStateIn(o: score.gen.meta.FieldMeta, state: Field.State.Value) = o.state_genMetaDef = state
  }

  val _genInitValue = new score.meta.std.fields.StringField[score.gen.meta.FieldMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Init Value"
      this.hint = ""
      this.defaultValue = "FieldInitValue"
    }

    override def getFrom(o: score.gen.meta.FieldMeta) = o.genInitValue
    override def setTo(o: score.gen.meta.FieldMeta, v: String) {o.genInitValue = v}
    override def getStateFrom(o: score.gen.meta.FieldMeta) = o.state_genInitValue
    override def setStateIn(o: score.gen.meta.FieldMeta, state: Field.State.Value) = o.state_genInitValue = state
  }

  val _genValue = new score.meta.std.fields.StringField[score.gen.meta.FieldMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Value"
      this.hint = ""
      this.defaultValue = "InlineValue"
    }

    override def getFrom(o: score.gen.meta.FieldMeta) = o.genValue
    override def setTo(o: score.gen.meta.FieldMeta, v: String) {o.genValue = v}
    override def getStateFrom(o: score.gen.meta.FieldMeta) = o.state_genValue
    override def setStateIn(o: score.gen.meta.FieldMeta, state: Field.State.Value) = o.state_genValue = state
  }

  fields = fields ++ Seq(_genSourceType, _genDef, _genMetaDef, _genInitValue, _genValue)

}

object FieldMetaMeta extends score.gen.meta.FieldMetaMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.gen.meta.FieldMeta = new score.gen.meta.FieldMeta()(objectContext)

  CompiledModel.registerEntity(this)
}
