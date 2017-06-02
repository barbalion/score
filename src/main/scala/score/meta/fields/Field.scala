package score.meta.fields

import score.Context
import score.meta.{Entity, EntityMeta, MetaObject}

object Field {

  object State extends Enumeration(0) {
    val Init, Default, Loaded, Modified = Value
  }

}

abstract class Field[E <: Entity, T](implicit context: Context) extends score.gen.meta.fields.Field {
  def compareIn(o1: E, o2: E): Int

  def equalsIn(o1: E, o2: E): Boolean

  def getFrom(o: E): T

  def isAssignedIn(o: E): Boolean

  def getStateFrom(o: E): Field.State.Value = Field.State.Init //todo: remove default implementation!

  def setStateIn(o: E, state: Field.State.Value) = {} //todo: remove default implementation!

  def setTo(o: E, v: T)

  def validateIn(o: E) = {}

  override def xmlName = MetaObject.decapitalizeFirstChar(super.xmlName)

  override def master: EntityMeta = super.master.asInstanceOf[EntityMeta]
}

