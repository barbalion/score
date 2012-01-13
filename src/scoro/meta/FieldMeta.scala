package scoro.meta

abstract class FieldMeta[E <: Entity, T](implicit aContext: Context[FieldMeta[E, T]], aContext2: Context[EntityProperty[FieldMeta[E, T]]]) extends EntityProperty[FieldMeta[E, T]] {
  var isKey = false;

  def compare(v1: T, v2: T): Int

  def getFrom(o: E): T
}