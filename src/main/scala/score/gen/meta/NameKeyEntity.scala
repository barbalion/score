package score.gen.meta

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

abstract class NameKeyEntity(implicit context: Context) extends score.meta.Entity {
  override def meta: score.meta.EntityMeta = score.gen.meta.NameKeyEntityMeta

  var raw_name: String = null
  def name = raw_name
  def name_=(v: String) {raw_name = v; setFieldState({state_name = _})}
  var state_name = Field.State.Init
}

abstract class NameKeyEntityMeta(implicit context: Context) extends score.meta.EntityMeta with XmlSerializableEntityMeta {

  this.name = "Name Key Entity"
  this.module = "Score"
  this.packagge = "Meta"
  this.isAbstract = true
  this.fields = fields

  private lazy val fieldsContext = new DetailContext(this)

  val _name = new score.meta.std.fields.StringField[score.gen.meta.NameKeyEntity]()(fieldsContext) {
    override def init(): Unit = {
      super.init()
      setAllFieldState(Field.State.Default)

      this.name = "Name"
      this.hint = "The name of the object. It must be unique within the scope. Can contain almost any characters including spaces and punctuation."
      this.isKey = true
      this.required = true
    }

    override def getFrom(o: score.gen.meta.NameKeyEntity) = o.name
    override def setTo(o: score.gen.meta.NameKeyEntity, v: String) {o.name = v}
    override def getStateFrom(o: score.gen.meta.NameKeyEntity) = o.state_name
    override def setStateIn(o: score.gen.meta.NameKeyEntity, state: Field.State.Value) = o.state_name = state
  }

  fields = fields ++ Seq(_name)

}

object NameKeyEntityMeta extends score.gen.meta.NameKeyEntityMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context) = throw new IllegalOperation("Cannot instantiate abstract entity.")

  CompiledModel.registerEntity(this)
}
