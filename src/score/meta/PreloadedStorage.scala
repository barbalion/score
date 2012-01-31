package score.meta;

trait PreloadedStorage extends Storage {
  private var preloadedObjects: Seq[Entity] = null;

  def preloaded = {
    synchronized({
      if (preloadedObjects == null) {
        preloadedObjects = objects()
      }
    })
    preloadedObjects
  }


  def list[E <: Entity](entityMeta: EntityMeta[E], condition: Condition, sorting: EntitySorting) = {
    val allObjectsOfA = preloaded filter (_.meta.name == entityMeta.name) map (_.asInstanceOf[Entity])

    val filteredObjects = if (condition == null) allObjectsOfA else allObjectsOfA filter (condition.check(_))

    val sortedObjects = if (sorting == null) filteredObjects else filteredObjects sortWith (sorting.compareObjects(_, _) < 0)

    sortedObjects map (_.asInstanceOf[E])
  }

  protected def reset() {preloadedObjects = null}

  protected def objects(): Seq[Entity];
}