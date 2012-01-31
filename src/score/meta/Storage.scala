package score.meta

abstract class Storage(protected val objectContext: Context)(implicit context: Context) extends MetaObject {

  def list[E <: Entity](entityMeta: EntityMeta[E], condition: Condition, sorting: EntitySorting): Seq[E]
  
  def loadObject(o: Entity);

  def insertObject(o: Entity);

  def updateObject(o: Entity);

  def deleteObject(o: Entity);
}

trait ReadOnlyStorage extends Storage {
  protected def operationNotSupported = {sys.error("Operation is not supported for read-only storage.")}


  override def deleteObject(o: Entity) {operationNotSupported}

  override def insertObject(o: Entity) {operationNotSupported}

  override def updateObject(o: Entity) {operationNotSupported}
}

trait WriteOnlyStorage extends Storage{
  protected def operationNotSupported = {sys.error("Operation is not supported for write-only storage.")}

  override def list[E <: Entity](entityMeta: EntityMeta[E], condition: Condition, sorting: EntitySorting) = {operationNotSupported}

  override def loadObject(o: Entity) {operationNotSupported}
}

class NullStorage extends Storage(null)(null) with ReadOnlyStorage with WriteOnlyStorage {
  protected override def operationNotSupported = {sys.error("Operation is not supported for null-storage.")}
}