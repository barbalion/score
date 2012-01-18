package scoro.meta

abstract class EntitySorting() {
  def compareObjects[A <: Entity](x1: A,  x2: A): Int
}

object NoSorting extends EntitySorting {
  def compareObjects[A <: Entity](x1: A, x2: A) = 0
}