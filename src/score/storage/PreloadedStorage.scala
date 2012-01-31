package score.storage

import score.meta._

trait PreloadedStorage extends Storage {
  private var _preloadedObjects: Seq[Entity] = null

  def preloadedObjects = {
    if (_preloadedObjects == null) {
      synchronized({
        if (_preloadedObjects == null) {
          _preloadedObjects = objects()
        }
      })
    }
    _preloadedObjects
  }

  override def list(entityMeta: EntityMeta, condition: Condition, sorting: EntitySorting): Seq[Entity] = {
    val allObjectsOfA = preloadedObjects filter (_.meta.is(entityMeta))

    val filteredObjects = if (condition == null) allObjectsOfA else allObjectsOfA filter condition.apply

    val sortedObjects = if (sorting == null) filteredObjects else filteredObjects sortWith (sorting.compareObjects(_, _) < 0)

    sortedObjects
  }

  def find(entityMeta: EntityMeta, condition: Condition) = list(entityMeta, condition, null).headOption

  protected def reset() {_preloadedObjects = null}

  protected def objects(): Seq[Entity]
}