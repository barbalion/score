package score.meta.model

import score.storage.{Storage, WriteOnlyStorage}
import score.meta.Entity
import score.{NotImplementedYet, SystemContext}

object CompiledModelStorage extends Storage(CompiledModelContext)(SystemContext) with WriteOnlyStorage {
  def deleteObject(o: Entity) = NotImplementedYet()

  def insertObject(o: Entity) = NotImplementedYet()

  def updateObject(o: Entity) = NotImplementedYet()
}

