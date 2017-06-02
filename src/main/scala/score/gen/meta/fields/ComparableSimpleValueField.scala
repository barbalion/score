package score.gen.meta.fields

import score._
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

abstract class ComparableSimpleValueField[E <: Entity, T <: Comparable[T]](implicit context: Context) extends score.meta.fields.SimpleValueField[E, T] {
  override def meta: score.meta.EntityMeta = score.gen.meta.fields.ComparableSimpleValueFieldMeta

}

abstract class ComparableSimpleValueFieldMeta(implicit context: Context) extends score.gen.meta.fields.SimpleValueFieldMeta with XmlSerializableEntityMeta {

  this.name = "Comparable Simple Value Field"
  this.hint = "An abstract ancestor for all comparable single value fields."
  this.module = "Score"
  this.packagge = "Meta Fields"
  this.extendz = score.gen.meta.fields.SimpleValueFieldMeta
  this.customized = true
  this.genClassParams = "ComparableSimpleValueFieldClassParams"
  this.genClassExtendParams = "FieldClassExtendParams"
  this.isAbstract = true
  this.fields = fields
  this.genSourceType = "TypedSourceType"
}

object ComparableSimpleValueFieldMeta extends score.gen.meta.fields.ComparableSimpleValueFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context) = throw new IllegalOperation("Cannot instantiate abstract entity.")

  CompiledModel.registerEntity(this)
}
