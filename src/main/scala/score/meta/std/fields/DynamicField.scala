package score.meta.fields

import score.meta._

trait DynamicField[E <: Entity, T] extends Field[E, T] {
  def getFrom(o: E): T = {
    o match {
      case o: DynamicEntity => o.applyDynamic(name).asInstanceOf[T]
      case _ => sys.error("Cannot get dynamic field from non-dynamic entity.")
    }
  }

  def setTo(o: E, v: T) {
    o match {
      case o: DynamicEntity => o.updateDynamic(name, v)
      case _ => sys.error("Cannot update dynamic field of non-dynamic entity.")
    }
  }
}

