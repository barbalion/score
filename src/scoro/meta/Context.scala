package scoro.meta

import java.util.Locale

abstract class Context[+E <: Entity](aModel: Model, aSession: Session, aStorage: Storage[E]) {
  val model = aModel
  val session = aSession;
  val storage = aStorage.asInstanceOf[Storage[Entity]]

  var locale = Locale.getDefault;

}