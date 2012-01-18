package scoro.meta

abstract class FieldMeta[E <: Entity, T](implicit context: Context[FieldMeta[E, T]]) extends EntityProperty[E, T] {
}

abstract class SimpleValueField[E <: Entity, T](implicit context: Context[FieldMeta[E,  T]]) extends FieldMeta[E, T] {
  protected def compareWithNulls(v1: T, v2: T) = if (v1 == null) if (v2 == null) 0 else -1 else if (v2 == null) 1 else compareValues(v1, v2)

  override def compareIn(o1: E, o2: E) = compareWithNulls(getFrom(o1), getFrom(o2))
  override def equalsIn(o1: E, o2: E) = compareIn(o1, o2) == 0
  def compareValues(v1: T, v2: T): Int
  def isAssignedIn(o: E) = getFrom(o) != null
}

abstract class SimpleComparableValueField[E <: Entity, T <: java.lang.Comparable[T]](implicit context: Context[FieldMeta[E,  T]]) extends SimpleValueField[E, T] {
  override def compareValues(v1: T, v2: T) = v1.compareTo(v2)
}