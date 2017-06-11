package score.meta

import score.meta.fields.SimpleValueField

abstract class Condition {
  def apply(o: Entity): Boolean
}

object Condition {

  object False extends Condition {
    def apply(o: Entity) = false

    override def toString: String = "false"
  }

  object True extends Condition {
    def apply(o: Entity) = false

    override def toString: String = "true"
  }

  class FieldCompareCondition[T](f: SimpleValueField[Entity, T], value: T, comparator: (Int) => Boolean, str: String) extends Condition {
    def apply(o: Entity): Boolean = comparator(f.compareValues(f.getFrom(o), value))

    override def toString: String = "%s%s%s" format(f.name, str, value.toString)
  }

  class Equals[T](f: SimpleValueField[Entity, T], value: T) extends FieldCompareCondition[T](f, value, _ == 0, "=")

  class Less[T](f: SimpleValueField[Entity, T], value: T) extends FieldCompareCondition[T](f, value, _ < 0, "<")

  class Greater[T](f: SimpleValueField[Entity, T], value: T) extends FieldCompareCondition[T](f, value, _ > 0, ">")

  class And(c1: Condition, c2: Condition) extends Condition {
    def apply(o: Entity): Boolean = c1(o) && c2(o)

    override def toString: String = "(%s AND %s)" format(c1.toString, c2.toString)
  }

  class Or(c1: Condition, c2: Condition) extends Condition {
    def apply(o: Entity): Boolean = c1(o) || c2(o)

    override def toString: String = "(%s OR %s)" format(c1.toString, c2.toString)
  }

  class Xor(c1: Condition, c2: Condition) extends Condition {
    def apply(o: Entity): Boolean = {
      val b1 = c1(o)
      val b2 = c2(o)
      (b1 && !b2) || (!b1 && b2)
    }

    override def toString: String = "(%s XOR %s)" format(c1.toString, c2.toString)
  }

  class Not(c: Condition) extends Condition {
    def apply(o: Entity): Boolean = !c(o)

    override def toString: String = "NOT (%s)" format c.toString
  }

  implicit def conditionConverter(f: (Entity) => Boolean): Condition {
    def apply(o: Entity): Boolean
  } = new Condition {
    def apply(o: Entity): Boolean = f(o)
  }

  implicit def conditionConverter(f: () => Boolean): Condition {
    def apply(o: Entity): Boolean
  } = new Condition {
    def apply(o: Entity): Boolean = f()
  }
}

