package score.meta

import xml.Elem

abstract class FieldMeta[E <: Entity, T](implicit context: Context) extends EntityProperty[E, T] {
  def sourceTypeName: String
}

abstract class SimpleValueField[E <: Entity, T](implicit context: Context) extends FieldMeta[E, T] with DynamicProperty[E, T] with SerializableProperty[E, T] {
  protected def compareWithNulls(v1: T, v2: T) = if (v1 == null) if (v2 == null) 0 else -1 else if (v2 == null) 1 else compareValues(v1, v2)

  override def compareIn(o1: E, o2: E) = compareWithNulls(getFrom(o1), getFrom(o2))
  override def equalsIn(o1: E, o2: E) = compareIn(o1, o2) == 0
  def compareValues(v1: T, v2: T): Int
  def isAssignedIn(o: E) = getFrom(o) != null

  def setFromXml(o: E, elem: Elem) {
    val node = elem(_.label == name)
    if (!node.isEmpty)
      setTo(o, parseXmlString(node.text));
  }

  def parseXmlString(v: String): T;
}

abstract class SimpleComparableValueField[E <: Entity, T <: java.lang.Comparable[T]](implicit context: Context) extends SimpleValueField[E, T] {
  override def compareValues(v1: T, v2: T) = v1.compareTo(v2)
}
