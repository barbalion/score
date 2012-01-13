package scoro.meta

abstract class Condition {
  def check[A <: Entity](o: A): Boolean

}