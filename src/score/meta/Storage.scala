package scoro.meta

abstract class Storage[E <: Entity](protected val objectContext: Context[E])(implicit context: Context[Storage[E]]) extends MetaObject {

  def list(entityMeta: EntityMeta[E], condition: Condition, sorting: EntitySorting): Seq[E]
  
  def loadObject(o: E);

  def insertObject(o: E);

  def updateObject(o: E);

  def deleteObject(o: E);

}

trait ReadOnlyStorage[E <: Entity] extends Storage[E] {
  protected def operationNotSupported = {sys.error("Operation is not supported for read-only storage.")}

  def deleteObject(o: E) {operationNotSupported}

  def insertObject(o: E) {operationNotSupported}

  def updateObject(o: E) {operationNotSupported}
}

trait WriteOnlyStorage[E <: Entity] extends Storage[E] {
  protected def operationNotSupported = {sys.error("Operation is not supported for write-only storage.")}

  def list(entityMeta: EntityMeta[E], condition: Condition, sorting: EntitySorting) = {operationNotSupported}

  def loadObject(o: E) {operationNotSupported}
}

class NullStorage[E <: Entity] extends Storage[E](null)(null) with ReadOnlyStorage[E] with WriteOnlyStorage[E] {
  protected override def operationNotSupported = {sys.error("Operation is not supported for null-storage.")}
}