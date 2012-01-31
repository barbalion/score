package score.meta

abstract class Condition {
  def check[A <: Entity](o: A): Boolean

}