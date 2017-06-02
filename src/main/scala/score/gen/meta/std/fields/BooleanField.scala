package score.gen.meta.std.fields

import score._
import score.meta.fields.Field
import score.meta._
import score.meta.model._
import score.meta.std.fields._
import score.xml._

class BooleanField[E <: Entity](implicit context: Context) extends score.meta.fields.SimpleValueField[E, Boolean] {
  override def meta: score.meta.EntityMeta = score.gen.meta.std.fields.BooleanFieldMeta

}

abstract class BooleanFieldMeta(implicit context: Context) extends score.gen.meta.fields.SimpleValueFieldMeta with XmlSerializableEntityMeta {

  this.name = "Boolean Field"
  this.hint = "Ancestor for all model meta objects in Score"
  this.xmlName = "Boolean"
  this.module = "Score"
  this.packagge = "Meta Std Fields"
  this.extendz = score.gen.meta.fields.SimpleValueFieldMeta
  this.customized = true
  this.genClassParams = "StdFieldClassParams"
  this.genClassRefParams = "StdFieldClassRefParams"
  this.genClassExtendParams = "StdFieldClassExtendParams"
  this.isAbstract = false
  this.fields = fields
  this.genSourceType = "BooleanSourceType"
  this.genInitValue = "BooleanInitValue"
}

object BooleanFieldMeta extends score.gen.meta.std.fields.BooleanFieldMeta()(CompiledModelContext) {
  override def newObject(implicit objectContext: Context): score.meta.std.fields.BooleanField[Entity] = new score.meta.std.fields.BooleanField[Entity]()(objectContext)

  CompiledModel.registerEntity(this)
}
