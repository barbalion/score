package score.gen.meta

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

abstract class MetaObject(implicit context: Context) extends score.gen.meta.NameKeyEntity {
  override def meta: score.meta.EntityMeta = score.gen.meta.MetaObjectMeta

  var raw_caption: String = null
  def caption = raw_caption
  def caption_=(v: String) {raw_caption = v; setFieldState({state_caption = _})}
  var state_caption = Field.State.Init

  var raw_hint: String = null
  def hint = raw_hint
  def hint_=(v: String) {raw_hint = v; setFieldState({state_hint = _})}
  var state_hint = Field.State.Init

  var raw_sourceName: String = null
  def sourceName = raw_sourceName
  def sourceName_=(v: String) {raw_sourceName = v; setFieldState({state_sourceName = _})}
  var state_sourceName = Field.State.Init

  var raw_sourceMetaName: String = null
  def sourceMetaName = raw_sourceMetaName
  def sourceMetaName_=(v: String) {raw_sourceMetaName = v; setFieldState({state_sourceMetaName = _})}
  var state_sourceMetaName = Field.State.Init

  var raw_xmlName: String = null
  def xmlName = raw_xmlName
  def xmlName_=(v: String) {raw_xmlName = v; setFieldState({state_xmlName = _})}
  var state_xmlName = Field.State.Init
}

abstract class MetaObjectMeta(implicit context: Context) extends score.gen.meta.NameKeyEntityMeta with XmlSerializableEntityMeta {

  this.name = "Meta Object"
  this.hint = "Ancestor for all model meta objects in Score"
  this.module = "Score"
  this.packagge = "Meta"
  this.extendz = score.gen.meta.NameKeyEntityMeta
  this.customized = true
  this.isAbstract = true
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _caption = new score.meta.std.fields.StringField[score.meta.MetaObject]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Caption"
      this.hint = "Human-readable caption as it will be known by a user. By default equals to Name."
    }

    override def getFrom(o: score.meta.MetaObject) = o.caption
    override def setTo(o: score.meta.MetaObject, v: String) {o.caption = v}
    override def getStateFrom(o: score.meta.MetaObject) = o.state_caption
    override def setStateIn(o: score.meta.MetaObject, state: Field.State.Value) = o.state_caption = state
  }

  val _hint = new score.meta.std.fields.StringField[score.meta.MetaObject]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Hint"
      this.hint = "A message (like this one) to be shown as a hint about the meta object."
    }

    override def getFrom(o: score.meta.MetaObject) = o.hint
    override def setTo(o: score.meta.MetaObject, v: String) {o.hint = v}
    override def getStateFrom(o: score.meta.MetaObject) = o.state_hint
    override def setStateIn(o: score.meta.MetaObject, state: Field.State.Value) = o.state_hint = state
  }

  val _sourceName = new score.meta.std.fields.StringField[score.meta.MetaObject]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Source Name"
      this.hint = "Name of the meta object in your generated sources. By default is equal to the Name. Spaces, underscores and other signs will split the name into tokens and then tokens will combine resulting Name for your sources."
    }

    override def getFrom(o: score.meta.MetaObject) = o.sourceName
    override def setTo(o: score.meta.MetaObject, v: String) {o.sourceName = v}
    override def getStateFrom(o: score.meta.MetaObject) = o.state_sourceName
    override def setStateIn(o: score.meta.MetaObject, state: Field.State.Value) = o.state_sourceName = state
  }

  val _sourceMetaName = new score.meta.std.fields.StringField[score.meta.MetaObject]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Source Meta Name"
      this.hint = "Similar to Source Name but this name is for meta object. By default equals to Source Name plus 'Meta'."
    }

    override def getFrom(o: score.meta.MetaObject) = o.sourceMetaName
    override def setTo(o: score.meta.MetaObject, v: String) {o.sourceMetaName = v}
    override def getStateFrom(o: score.meta.MetaObject) = o.state_sourceMetaName
    override def setStateIn(o: score.meta.MetaObject, state: Field.State.Value) = o.state_sourceMetaName = state
  }

  val _xmlName = new score.meta.std.fields.StringField[score.meta.MetaObject]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Xml Name"
      this.hint = "Name of xml nodes to store the field value. By default is equal to source field name. You can override it with your own implementation here."
    }

    override def getFrom(o: score.meta.MetaObject) = o.xmlName
    override def setTo(o: score.meta.MetaObject, v: String) {o.xmlName = v}
    override def getStateFrom(o: score.meta.MetaObject) = o.state_xmlName
    override def setStateIn(o: score.meta.MetaObject, state: Field.State.Value) = o.state_xmlName = state
  }

  fields = fields ++ Seq(_caption, _hint, _sourceName, _sourceMetaName, _xmlName)

}

object MetaObjectMeta extends score.gen.meta.MetaObjectMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context) = throw new IllegalOperation("Cannot instantiate abstract entity.")

  CompiledModel.registerEntity(this)
}
