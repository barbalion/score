package score.meta

import score.gen.meta.NameKeyEntity
import score.meta.fields.Field
import score.{NotImplementedYet, Context, DetailContext, IllegalOperation}

import scala.collection.mutable

object Entity {

  object State extends Enumeration {
    val Init, New, Loaded, Modified, ToDelete, Deleted, Saved = Value
    type State = Value
  }

}

abstract class Entity(implicit val context: Context) {
  //noinspection LanguageFeature
  implicit protected def allMetaToEntityMeta[M <: EntityMeta](x: M): EntityMeta = x.asInstanceOf[EntityMeta]
  //  implicit def AutoBoxing[A](a: A): () => A = { () => a }
  //  implicit def AutoUnboxing[A](a: () => A): A = { a() }

  def meta: EntityMeta

  def master = context match {
    case dc: DetailContext => dc.master
    case _ => sys.error("Entity is not detail.")
  }

  def isDetail = context.isInstanceOf[DetailContext]

  protected var _state = Entity.State.New

  def state = _state

  def init(): Unit = {
    if (state == Entity.State.Init) {
      //      meta.fields foreach {
      //        f => f.setDefaultTo(this)
      //      }
      _state = Entity.State.New
    }
    context.onEntityCreated(this)
  }

  def isModified = state == Entity.State.Modified

  def isNew = state == Entity.State.New

  //  def modifiedFields = meta.fields.filter(_.getStateFrom(this) == Field.State.Modified)

  protected def validate(): Unit = {
    meta.fields foreach {
      _.validateIn(this)
    }
  }

  def save(): Entity = {
    validate()
    _state match {
      case Entity.State.New =>
        context.storage.insertObject(this)
        _state = Entity.State.Saved
      case Entity.State.Modified =>
        context.storage.updateObject(this)
        _state = Entity.State.Saved
      case Entity.State.ToDelete =>
        context.storage.deleteObject(this)
        _state = Entity.State.Deleted
      case Entity.State.Loaded =>
        _state = Entity.State.Saved
      case Entity.State.Saved => // do nothing
      case Entity.State.Deleted => // do nothing
      case state => sys.error("Trying to save the entity in a wrong state: %s" format state)
    }
    context.onEntitySaved(this)
    this
  }

  def delete(): Entity = {
    _state match {
      case Entity.State.New =>
        _state = Entity.State.Deleted
      case Entity.State.Modified =>
        _state = Entity.State.ToDelete
        context.onEntityModified(this)
      case Entity.State.Loaded =>
        _state = Entity.State.ToDelete
        context.onEntityModified(this)
      case state => sys.error("Trying to delete the entity in a wrong state: %s" format state)
    }
    this
  }

  protected def setAllFieldState(state: Field.State.Value) = meta.fields foreach { f => f.setStateIn(this, Field.State.Default) }

  protected def checkKeyAssigned() {
    if (!isKeyAssigned)
      sys.error("Can't load an object without a key!")
  }

  def apply(fieldName: String) = meta.fieldByName(fieldName).getFrom(this)

  def update(fieldName: String, v: Any) {
    meta.fieldByName(fieldName).setTo(this, v)
  }

  private var enteredToString = false
  private val toStringMutex = "toStringMutex"

  override def toString: String = {
    if (!enteredToString) toStringMutex synchronized {
      if (!enteredToString) {
        enteredToString = true
        try {
          val sb = new StringBuilder()
          sb.append(super.toString).append("{\n")
          meta.fields foreach { f =>
            val v: Any = f.getFrom(this)
            v match {
              case null =>
              case e: NameKeyEntity => sb.append(f.name).append("=<Entity named \"").append(e.name).append("\">\n")
              case e: Entity => sb.append(f.name).append("=<Entity of class \"").append(e.meta.name).append("\">\n")
              case _ => sb.append(f.name).append("=").append(v.toString).append("\n")
            }
          }
          sb.append("}\n")
          sb.toString()
        } finally {
          enteredToString = false
        }
      } else "<circular>"
    } else "<circular>"
  }

  def load() {
    checkKeyAssigned()
    context.storage.loadObject(this)
    setAllFieldState(Field.State.Loaded)
    _state = Entity.State.Loaded
    this
  }

  protected def setFieldState(objStateSetter: (Field.State.Value) => Unit) = {
    state match {
      case Entity.State.Init => objStateSetter(Field.State.Init)
      case Entity.State.Loaded =>
        objStateSetter(Field.State.Modified)
        _state = Entity.State.Modified
        context.onEntityModified(this)
      case Entity.State.New | Entity.State.Modified =>
        objStateSetter(Field.State.Modified)
      case Entity.State.Saved =>
        sys.error("Cannot modify saved object. Reload it first.");
      case Entity.State.Deleted | Entity.State.ToDelete =>
        sys.error("Cannot modify deleted object.");
      case _ => NotImplementedYet
    }
  }

  def assignFrom[A <: Entity](source: A) {
    meta.fields foreach { f => f.setTo(this, f.getFrom(source)); f.setStateIn(this, f.getStateFrom(source))}
  }

  override def equals(obj: Any) = obj match {
    case o: Entity => meta.isKeyEqual(this, o)
    case other => false
  }

  def isKeyAssigned: Boolean = meta.key forall (f => f.isAssignedIn(this))

  def lazyLoad() {
    if (state == Entity.State.New && isKeyAssigned) {
      load()
    }
  }

}

trait ReadOnlyEntity extends Entity {
  override def save(): Entity = throw new IllegalOperation("Trying to save read-only object.")

}

trait WriteOnlyEntity extends Entity {
  override def load() {
    throw new IllegalOperation("Trying to load write-only object.")
  }
}

class DynamicEntity(aMeta: EntityMeta)(implicit context: Context) extends Entity {
  def meta = aMeta

  protected val dynamicValues = new mutable.HashMap[String, Any]
  protected val dynamicStates = new mutable.HashMap[String, Field.State.Value]

  override def apply(fieldName: String) = applyDynamic(fieldName)

  override def update(fieldName: String, v: Any) {
    updateDynamic(fieldName, v)
  }

  def applyDynamic(fieldName: String) = dynamicValues(fieldName)

  def updateDynamic(fieldName: String, v: Any) {
    dynamicValues.put(fieldName, v)
    setFieldState({
      dynamicStates.put(fieldName, _)
    })
  }

}
