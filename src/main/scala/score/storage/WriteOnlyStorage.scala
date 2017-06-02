package score.storage

import score.meta._

trait WriteOnlyStorage extends Storage{
  protected def operationNotSupported = {sys.error("Operation is not supported for write-only storage.")}

  override def list(entityMeta: EntityMeta, condition: Condition, sorting: EntitySorting) = {operationNotSupported}

  override def loadObject(o: Entity) {operationNotSupported}
}

