package score.meta.model

import score.gen.MetaModel
import score.meta.{EntityMeta, Entity}
import score.storage._
import score.{Context, SystemContext, SystemSession}

object CompiledModelContext extends Context(CompiledModel, SystemSession, new MemoryStorage(SystemContext)(SystemContext)) {

  MetaModel

  override def onEntityCreated(e: Entity): Unit = {
    super.onEntityCreated(e)
    e match {
      case e: EntityMeta => CompiledModel.registerEntity(e)
      case _ => sys.error("Unexpected object in Compiled Model: %s" format e)
    }
  }
}

