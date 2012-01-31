package score.storage

import score.meta._
import model.Model
import score.{NotImplementedYet, Context}

abstract class Storage(protected val objectContext: Context)(implicit context: Context) extends MetaObject {

  def list(entityMeta: EntityMeta, condition: Condition, sorting: EntitySorting): Seq[Entity]

  def list(entityMeta: EntityMeta, condition: Condition): Seq[Entity] = list(entityMeta, condition, null)

  def loadObject(o: Entity)

  def insertObject(o: Entity)

  def updateObject(o: Entity)

  def deleteObject(o: Entity)

  override def meta = NotImplementedYet()

  def error(msg: String) = new StorageException(this, msg)

  def objectNotFound = new StorageException(this, "Object not found.")

  def objectNotFound[E <: Entity, T](o: E) =
    throw new StorageException(this, "Object not found: %s" format o.meta.key.foldLeft("")((s, f) => "%s\n\"%s\"=\"%s\"" format(s, f.name, f.getFrom(o))))

}

