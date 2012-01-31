package score.meta.fields

import score.meta._
import score.Context

abstract class ComparableSimpleValueField[E <: Entity, T <: Comparable[T]](implicit context: Context) extends score.gen.meta.fields.ComparableSimpleValueField[E, T] {
  override def compareValues(v1: T, v2: T) = v1.compareTo(v2)
}

