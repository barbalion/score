package score.meta

abstract class Context(val model: Model, val session: Session, val storage: Storage) {
}

class ModelLoadingContext(aStorage: Storage) extends Context(null, SystemSession, aStorage) {

}

object SystemContext extends Context(null, SystemSession, new NullStorage) {

}

object CompiledModelContext extends Context(CompiledModel, SystemSession, new NullStorage) {
  {
    // init model
    (
      EntityMetaMeta,
      StringFieldMeta,
      IntegerFieldMeta,
      null)
  }

}