package scoro.meta;

trait PreloadedStorage[A <: Entity] extends Storage[A] {
  private var preloadedObjects: Seq[A] = null;

  def preloaded = {
    synchronized({
      if (preloadedObjects == null) {
        preloadedObjects = objects()
      }
    })
    preloadedObjects
  }

  override def list(entityMeta: EntityMeta[A], condition: Condition, sorting: EntitySorting) = {
    val allObjectsOfA = preloaded filter (_.meta.name == entityMeta.name) map (_.asInstanceOf[A])

    val filteredObjects = if (condition == null) allObjectsOfA else allObjectsOfA filter (condition.check(_))

    if (sorting == null) filteredObjects else filteredObjects sortWith (sorting.compareObjects(_, _) < 0)
  }

  protected def reset() {preloadedObjects = null}

  protected def objects(): Seq[A];
}