package score.meta.std.fields

import score.Context
import score.meta.fields.DynamicField
import score.meta.model.BadMetaModel
import score.meta.{Entity, _}
import score.xml._

import scala.xml.Elem

class RefField[E <: Entity, T <: Entity](implicit context: Context)
  extends score.gen.meta.std.fields.RefField[E, T] with DynamicField[E, Ref[T]] with XmlSerializableField[E, Ref[T]] {

  override def compareValues(v1: Ref[T], v2: Ref[T]) = v1().hashCode() - v2().hashCode()

  def setFromXml(o: E, el: Elem, prefix: String) = {
    to() match {
      case refMeta: XmlSerializableEntityMeta =>
        refMeta.keyCondition(el, xmlName, o.context) foreach {c => setTo(o, new KeyRef(to, c, o.context))}
      case null => BadMetaModel(this, "Ref without \"to\".")
    }
  }
}

abstract class Ref[A <: Entity] {
  def apply(): A
}

class LoadedObjectRef[A <: Entity](val obj: A) extends Ref[A]() {
  override def apply(): A = obj
}

class KeyRef[A <: Entity](meta: EntityMeta, key: Condition, objectContext: Context) extends Ref[A]() {
  override def apply(): A = objectContext.storage.list(meta, key) match {
    case Nil => sys.error("Object \"%s\" not found (%s)." format (meta.name, key.toString))
    case head :: tail :: _ => sys.error("Too many objects found for key \"%s\"." format key)
    case o :: _ => o.asInstanceOf[A]
  }
}

object Ref {
  implicit def RefConversionUnbox[A <: Entity](ref: Ref[A]): A = ref()
  implicit def RefConversionBox[A <: Entity](obj: A): Ref[A] = new LoadedObjectRef[A](obj)
}

