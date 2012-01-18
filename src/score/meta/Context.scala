package scoro.meta

abstract class Context[+E <: Entity](val model: Model, val session: Session, aStorage: Storage[E]) {
  val storage = aStorage.asInstanceOf[Storage[Entity]]

}

class ModelLoadingContext(aStorage: Storage[MetaObject]) extends Context[MetaObject](null, SystemSession, aStorage) {

}

object SystemContext extends Context[Nothing](null, SystemSession, new NullStorage[Nothing]) {

}

object RunTimeModelContext extends Context[Nothing](RunTimeModel, SystemSession, new NullStorage[Nothing]) {

}