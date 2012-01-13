package scoro.meta

trait ReadOnlyStorage[E <: Entity] extends Storage[E] {

  def notSupported = {sys.error("This storage is read-only. Operation is not supported.")}

  override def insertObject(o: E) {notSupported}

  override def updateObject(o: E) {notSupported}

  override def deleteObject(o: E) {notSupported}


}