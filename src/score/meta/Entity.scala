package scoro.meta

abstract class Entity(implicit val context: Context[Entity]) {

  private var fNew = false;
  def isNew = fNew;

  protected var loaded = false;

  protected var modifiedFields: List[String] = Nil;

  protected def validate();

  def save() {
    validate();
    if (isNew)
      context.storage.insertObject(this)
    else
      context.storage.updateObject(this)
  }

  def checkKeyAssigned() {
    if (!isKeyAssigned)
      sys.error("Cannot load object without key!");
  };
  
  def apply(fieldName: String) = meta.fieldByName(fieldName).getFrom(this)

  def load() {
    checkKeyAssigned()
    context.storage.loadObject(this)
  }

  def meta: EntityMeta[Entity]

  def assignFrom[A <: Entity](source: A) {
    sys.error("Operation is not supported.")
  }

  override def equals(obj: Any) = if (obj.isInstanceOf[Entity]) isKeyEqualTo(obj.asInstanceOf[Entity]) else false

  protected def isKeyEqualTo(o: Entity): Boolean = meta.key forall (f => f.compareIn(this, o) == 0)
  
  protected def isKeyAssigned: Boolean = meta.key forall (f => f.isAssignedIn(this))

  protected def lazyLoad() {
    if (!isNew && !loaded) {
      load()
    }
  }
}

trait ReadOnlyEntity extends Entity {
  override def save() {
    throw new IllegalOperation("Trying to save read-only object.");
  }
}

trait WriteOnlyEntity extends Entity {
  override def load() {
    throw new IllegalOperation("Trying to load write-only object.");
  }
}
