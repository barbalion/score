package scoro.meta

abstract class EntityProperty[E <: Entity, T](implicit context: Context[EntityProperty[E,  T]]) extends MetaObject {
  var isKey = false;

  def compareIn(o1: E, o2: E): Int

  def equalsIn(o1: E, o2: E): Boolean
  
  def getFrom(o: E): T

  def isAssignedIn(o: E): Boolean

  def setTo(o: E, v: T)
}

