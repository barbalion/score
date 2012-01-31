package score.storage

import score.meta.Entity
import score.Context

class MemoryStorage(objectContext: Context)(implicit context: Context) extends Storage(objectContext)(context) with PreloadedStorage {
  private var fObjects: List[Entity] = Nil

  protected def objects(): Seq[Entity] = fObjects

  def loadObject(o: Entity): Unit = find(o.meta, o.meta.keyCondition(o)) match {
    case Some(found) => o.assignFrom(found)
    case _ => objectNotFound(o)
  }

  def insertObject(o: Entity): Unit = {
    fObjects = o :: fObjects
    reset()
  }

  def deleteObject(o: Entity): Unit = {
    fObjects = fObjects filter {
      !o.meta.isKeyEqual(o, _)
    }
    reset()
  }

  def updateObject(o: Entity): Unit = {
    deleteObject(o)
    insertObject(o)
  }
}
