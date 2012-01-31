package score.storage

import score.meta._

trait ReadOnlyStorage extends Storage {
  protected def operationNotSupported = {sys.error("Operation is not supported for read-only storage.")}

  override def deleteObject(o: Entity) = operationNotSupported

  override def insertObject(o: Entity) = operationNotSupported

  override def updateObject(o: Entity) = operationNotSupported
}

