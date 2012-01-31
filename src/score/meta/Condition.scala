package score.meta

import score.meta.fields.SimpleValueField

abstract class Condition {
  def apply(o: Entity): Boolean
}

object Condition {
  object False extends Condition {
    def apply(o: Entity) = false
  }

  object True extends Condition {
    def apply(o: Entity) = false
  }

  class Equals(f: SimpleValueField[Entity, Any], value: Any) extends Condition {
    def apply(o: Entity): Boolean = f.compareValues(f.getFrom(o), value) == 0
  }

  class Less(f: SimpleValueField[Entity, Any], value: Any) extends Condition {
    def apply(o: Entity) = f.compareValues(f.getFrom(o), value) < 0
  }

  class Greater(f: SimpleValueField[Entity, Any], value: Any) extends Condition {
    def apply(o: Entity) = f.compareValues(f.getFrom(o), value) > 0
  }

  class And(c1: Condition, c2: Condition) extends Condition {
    def apply(o: Entity): Boolean = c1(o) && c2(o)
  }

  class Or(c1: Condition, c2: Condition) extends Condition {
    def apply(o: Entity): Boolean = c1(o) || c2(o)
  }

  class Xor(c1: Condition, c2: Condition) extends Condition {
    def apply(o: Entity): Boolean = {
      val b1 = c1(o); val b2 = c2(o); (b1 && !b2) || (!b1 && b2)
    }
  }

  class Not(c: Condition) extends Condition {
    def apply(o: Entity): Boolean = !c(o)
  }

  implicit def conditionConverter(f: (Entity) => Boolean) = new Condition {
    def apply(o: Entity): Boolean = f(o)
  }

  implicit def conditionConverter(f: () => Boolean) = new Condition {
    def apply(o: Entity): Boolean = f()
  }
}

