package score.gen.meta

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class EntityMeta(implicit context: Context) extends score.meta.MetaObject {
  override def meta: score.meta.EntityMeta = score.gen.meta.EntityMetaMeta

  var raw_module: String = null
  def module = raw_module
  def module_=(v: String) {raw_module = v; setFieldState({state_module = _})}
  var state_module = Field.State.Init

  var raw_packagge: String = null
  def packagge = raw_packagge
  def packagge_=(v: String) {raw_packagge = v; setFieldState({state_packagge = _})}
  var state_packagge = Field.State.Init

  var raw_extendz: Ref[score.meta.EntityMeta] = null
  def extendz = raw_extendz
  def extendz_=(v: Ref[score.meta.EntityMeta]) {raw_extendz = v; setFieldState({state_extendz = _})}
  var state_extendz = Field.State.Init

  var raw_comparable: Boolean = false
  def comparable = raw_comparable
  def comparable_=(v: Boolean) {raw_comparable = v; setFieldState({state_comparable = _})}
  var state_comparable = Field.State.Init

  var raw_customized: Boolean = false
  def customized = raw_customized
  def customized_=(v: Boolean) {raw_customized = v; setFieldState({state_customized = _})}
  var state_customized = Field.State.Init

  var raw_customizedMeta: Boolean = false
  def customizedMeta = raw_customizedMeta
  def customizedMeta_=(v: Boolean) {raw_customizedMeta = v; setFieldState({state_customizedMeta = _})}
  var state_customizedMeta = Field.State.Init

  var raw_customMeta: Ref[score.meta.EntityMeta] = null
  def customMeta = raw_customMeta
  def customMeta_=(v: Ref[score.meta.EntityMeta]) {raw_customMeta = v; setFieldState({state_customMeta = _})}
  var state_customMeta = Field.State.Init

  var raw_customMetaFor: Ref[score.meta.EntityMeta] = null
  def customMetaFor = raw_customMetaFor
  def customMetaFor_=(v: Ref[score.meta.EntityMeta]) {raw_customMetaFor = v; setFieldState({state_customMetaFor = _})}
  var state_customMetaFor = Field.State.Init

  var raw_genConstructor: String = "MetaObjectConstructor"
  def genConstructor = raw_genConstructor
  def genConstructor_=(v: String) {raw_genConstructor = v; setFieldState({state_genConstructor = _})}
  var state_genConstructor = Field.State.Init

  var raw_genClassParams: String = null
  def genClassParams = raw_genClassParams
  def genClassParams_=(v: String) {raw_genClassParams = v; setFieldState({state_genClassParams = _})}
  var state_genClassParams = Field.State.Init

  var raw_genClassRefParams: String = null
  def genClassRefParams = raw_genClassRefParams
  def genClassRefParams_=(v: String) {raw_genClassRefParams = v; setFieldState({state_genClassRefParams = _})}
  var state_genClassRefParams = Field.State.Init

  var raw_genClassExtendParams: String = null
  def genClassExtendParams = raw_genClassExtendParams
  def genClassExtendParams_=(v: String) {raw_genClassExtendParams = v; setFieldState({state_genClassExtendParams = _})}
  var state_genClassExtendParams = Field.State.Init

  var raw_isAbstract: Boolean = false
  def isAbstract = raw_isAbstract
  def isAbstract_=(v: Boolean) {raw_isAbstract = v; setFieldState({state_isAbstract = _})}
  var state_isAbstract = Field.State.Init

  var raw_fields: Seq[score.meta.fields.Field[Entity, Any]] = Nil
  def fields = raw_fields
  def fields_=(v: Seq[score.meta.fields.Field[Entity, Any]]) {raw_fields = v; setFieldState({state_fields = _})}
  var state_fields = Field.State.Init
}

abstract class EntityMetaMeta(implicit context: Context) extends score.gen.meta.MetaObjectMeta with XmlSerializableEntityMeta {

  this.name = "Entity Meta"
  this.hint = "Score Entity Definition"
  this.xmlName = "Entity"
  this.module = "Score"
  this.packagge = "Meta"
  this.extendz = score.gen.meta.MetaObjectMeta
  this.customized = true
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _module = new score.meta.std.fields.StringField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Module"
      this.hint = "Name of the module which meta object belongs to."
      this.required = true
    }

    override def getFrom(o: score.meta.EntityMeta) = o.module
    override def setTo(o: score.meta.EntityMeta, v: String) {o.module = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_module
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_module = state
  }

  val _packagge = new score.meta.std.fields.StringField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Package"
      this.hint = "Package name inside the module."
    }

    override def getFrom(o: score.meta.EntityMeta) = o.packagge
    override def setTo(o: score.meta.EntityMeta, v: String) {o.packagge = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_packagge
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_packagge = state
  }

  val _extendz = new score.meta.std.fields.RefField[score.meta.EntityMeta, score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Extends"
      this.hint = "The name of ancestor meta object. 'Entity' is the root and default value."
      this.required = true
      this.to = score.gen.meta.EntityMetaMeta
    }

    override def getFrom(o: score.meta.EntityMeta) = o.extendz
    override def setTo(o: score.meta.EntityMeta, v: Ref[score.meta.EntityMeta]) {o.extendz = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_extendz
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_extendz = state
  }

  val _comparable = new score.meta.std.fields.BooleanField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Comparable"
      this.hint = "Wheather the entity's objects are comparable with each other. False by default."
    }

    override def getFrom(o: score.meta.EntityMeta) = o.comparable
    override def setTo(o: score.meta.EntityMeta, v: Boolean) {o.comparable = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_comparable
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_comparable = state
  }

  val _customized = new score.meta.std.fields.BooleanField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Customized"
      this.hint = "Wheather the entity's object has custom source class overriding."
    }

    override def getFrom(o: score.meta.EntityMeta) = o.customized
    override def setTo(o: score.meta.EntityMeta, v: Boolean) {o.customized = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_customized
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_customized = state
  }

  val _customizedMeta = new score.meta.std.fields.BooleanField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Customized Meta"
      this.hint = "Wheather the entity's object has custom source meta class overriding."
    }

    override def getFrom(o: score.meta.EntityMeta) = o.customizedMeta
    override def setTo(o: score.meta.EntityMeta, v: Boolean) {o.customizedMeta = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_customizedMeta
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_customizedMeta = state
  }

  val _customMeta = new score.meta.std.fields.RefField[score.meta.EntityMeta, score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Custom Meta"
      this.hint = "The name of custom meta object for this entity. Must be used with 'Custom Meta For' on other side."
      this.to = score.gen.meta.EntityMetaMeta
    }

    override def getFrom(o: score.meta.EntityMeta) = o.customMeta
    override def setTo(o: score.meta.EntityMeta, v: Ref[score.meta.EntityMeta]) {o.customMeta = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_customMeta
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_customMeta = state
  }

  val _customMetaFor = new score.meta.std.fields.RefField[score.meta.EntityMeta, score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Custom Meta For"
      this.hint = "The name of customized meta object. Must be used with 'Custom Meta' on other side."
      this.to = score.gen.meta.EntityMetaMeta
    }

    override def getFrom(o: score.meta.EntityMeta) = o.customMetaFor
    override def setTo(o: score.meta.EntityMeta, v: Ref[score.meta.EntityMeta]) {o.customMetaFor = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_customMetaFor
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_customMetaFor = state
  }

  val _genConstructor = new score.meta.std.fields.StringField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Constructor"
      this.hint = ""
      this.defaultValue = "MetaObjectConstructor"
    }

    override def getFrom(o: score.meta.EntityMeta) = o.genConstructor
    override def setTo(o: score.meta.EntityMeta, v: String) {o.genConstructor = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_genConstructor
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_genConstructor = state
  }

  val _genClassParams = new score.meta.std.fields.StringField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Class Params"
      this.hint = ""
    }

    override def getFrom(o: score.meta.EntityMeta) = o.genClassParams
    override def setTo(o: score.meta.EntityMeta, v: String) {o.genClassParams = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_genClassParams
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_genClassParams = state
  }

  val _genClassRefParams = new score.meta.std.fields.StringField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Class Ref Params"
      this.hint = ""
    }

    override def getFrom(o: score.meta.EntityMeta) = o.genClassRefParams
    override def setTo(o: score.meta.EntityMeta, v: String) {o.genClassRefParams = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_genClassRefParams
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_genClassRefParams = state
  }

  val _genClassExtendParams = new score.meta.std.fields.StringField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Gen Class Extend Params"
      this.hint = ""
    }

    override def getFrom(o: score.meta.EntityMeta) = o.genClassExtendParams
    override def setTo(o: score.meta.EntityMeta, v: String) {o.genClassExtendParams = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_genClassExtendParams
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_genClassExtendParams = state
  }

  val _isAbstract = new score.meta.std.fields.BooleanField[score.meta.EntityMeta]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Is Abstract"
      this.hint = "Wheather the entity is only abstract superclass for descendants or not. Abstract entities cannot be instantiated."
    }

    override def getFrom(o: score.meta.EntityMeta) = o.isAbstract
    override def setTo(o: score.meta.EntityMeta, v: Boolean) {o.isAbstract = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_isAbstract
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_isAbstract = state
  }

  val _fields = new score.meta.std.fields.ListField[score.meta.EntityMeta, score.meta.fields.Field[Entity, Any]]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Fields"
      this.hint = "List of features."
      this.genValue = "FieldsMeta"
      this.of = score.gen.meta.fields.FieldMeta
    }

    override def getFrom(o: score.meta.EntityMeta) = o.fields
    override def setTo(o: score.meta.EntityMeta, v: Seq[score.meta.fields.Field[Entity, Any]]) {o.fields = v}
    override def getStateFrom(o: score.meta.EntityMeta) = o.state_fields
    override def setStateIn(o: score.meta.EntityMeta, state: Field.State.Value) = o.state_fields = state
  }

  fields = fields ++ Seq(_module, _packagge, _extendz, _comparable, _customized, _customizedMeta, _customMeta, _customMetaFor, _genConstructor, _genClassParams, _genClassRefParams, _genClassExtendParams, _isAbstract, _fields)

}

object EntityMetaMeta extends score.gen.meta.EntityMetaMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.meta.EntityMeta = new score.meta.EntityMeta()(objectContext)

  CompiledModel.registerEntity(this)
}
