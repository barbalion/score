package score.gen.meta.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

abstract class SimpleValueField[E <: Entity, T](implicit context: Context) extends score.meta.fields.Field[E, T] {
  override def meta: score.meta.EntityMeta = score.gen.meta.fields.SimpleValueFieldMeta

}

abstract class SimpleValueFieldMeta(implicit context: Context) extends score.gen.meta.fields.FieldMeta with XmlSerializableEntityMeta {

  this.name = "Simple Value Field"
  this.hint = "An abstract ancestor for all fields that can store value as single object."
  this.module = "Score"
  this.packagge = "Meta Fields"
  this.extendz = score.gen.meta.fields.FieldMeta
  this.customized = true
  this.genClassParams = "FieldClassParams"
  this.genClassRefParams = "FieldClassRefParams"
  this.genClassExtendParams = "FieldClassExtendParams"
  this.isAbstract = true
  this.fields = fields
  this.genSourceType = "TypedSourceType"
}

object SimpleValueFieldMeta extends score.gen.meta.fields.SimpleValueFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context) = throw new IllegalOperation("Cannot instantiate abstract entity.")

  CompiledModel.registerEntity(this)
}
