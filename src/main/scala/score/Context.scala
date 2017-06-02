package score
import meta.model.Model
import score.meta.Entity
import score.storage._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

abstract class Context(val model: Model, val session: Session, val storage: Storage) {
  protected var entitiesCreated: mutable.ListBuffer[Entity] = new ListBuffer[Entity]()
  protected var entitiesModified: mutable.ListBuffer[Entity] = new ListBuffer[Entity]()

  def onEntityCreated(e: Entity) = {
    entitiesCreated.append(e)
  }

  def onEntityModified(e: Entity) = {
    entitiesModified.append(e)
  }

  def onEntitySaved(e: Entity) = {
  }

  def commit = {
    entitiesModified foreach (e => e.save())
    entitiesModified.clear()
  }

  def rollback = {
    entitiesCreated.clear()
    entitiesModified.clear()
  }
}

class DetailContext(val master: Entity) extends Context(master.context.model, master.context.session, master.context.storage){
}

object SystemContext extends Context(null, SystemSession, new NullStorage) {
}

