package score.meta.fields

import score.meta._
import score.{NotImplementedYet, Context}

abstract class SimpleValueField[E <: Entity, T](implicit context: Context) extends score.gen.meta.fields.SimpleValueField[E, T] with DynamicField[E, T] {
  override def compareIn(o1: E, o2: E) = compareWithNulls(getFrom(o1), getFrom(o2))

  override def equalsIn(o1: E, o2: E) = compareIn(o1, o2) == 0

  protected def compareWithNulls(v1: T, v2: T) = if (v1 == null) if (v2 == null) 0 else -1 else if (v2 == null) 1 else compareValues(v1, v2)

  def compareValues(v1: T, v2: T): Int  = NotImplementedYet() // todo: remove default implementation

  def isAssignedIn(o: E) = getFrom(o) != null

//  override def validateIn(o: E): Unit = if (required && getFrom(o) == null) NotImplementedYet()
}

