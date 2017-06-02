package score.meta.model

import score.meta.{Entity, EntityMeta}

object CompiledModel extends Model(Nil) {

  def registerEntity[E <: Entity](meta: EntityMeta) {
    aEntities = meta.asInstanceOf[EntityMeta] :: aEntities
  }

  def registerEntities[E <: Entity](metas: Seq[EntityMeta]) {
    aEntities = metas.toList ++ aEntities
  }
}

