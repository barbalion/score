package scoro.meta

abstract class IntegerField[E <: Entity](implicit context: Context[FieldMeta[E, Int]]) extends SimpleValueField[E, Int] {
  def compareValues(v1: Int, v2: Int) = v1.compareTo(v2)
}

abstract class StringField[E <: Entity](implicit context: Context[FieldMeta[E, String]]) extends SimpleComparableValueField[E, String] {
}

abstract class BooleanField[E <: Entity](implicit context: Context[FieldMeta[E, Boolean]]) extends SimpleValueField[E, Boolean] {
  def compareValues(v1: Boolean, v2: Boolean) = v1.compareTo(v2)
}

abstract class DoubleField[E <: Entity](implicit context: Context[FieldMeta[E, Double]]) extends SimpleValueField[E, Double] {
  def compareValues(v1: Double, v2: Double) = v1.compareTo(v2)
}

