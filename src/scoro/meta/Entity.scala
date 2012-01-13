package scoro.meta

abstract class Entity(implicit aContext: Context[Entity]) {
  val context = aContext;

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
    //todo
  };

  def load() {
    checkKeyAssigned()
    context.storage.loadObject(this)
  }

  def meta: EntityMeta[Entity]

  def assignFrom[A <: Entity](source: A) {
    sys.error("Operation is not supported.")
  }

  override def equals(obj: AnyRef) = if (obj.isInstanceOf[Entity]) keyEquals(obj.asInstanceOf[Entity]) else false

  def keyEquals(o: Entity): Boolean = meta.key forall (f => f.compare(f.getFrom(this), f.getFrom(o)))

  protected def lazyLoad() {
    if (!isNew && !loaded) {
      load()
    }
  }
}
