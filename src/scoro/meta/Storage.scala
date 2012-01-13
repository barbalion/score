package scoro.meta

abstract class Storage[E <: Entity](objectContext: Context[E])(implicit aContext: Context[Storage[E]]) extends MetaObject[Storage[E]] {

  def list(entityMeta: EntityMeta[E], condition: Condition, sorting: EntitySorting): Seq[E]
  
  def loadObject(o: E);

  def insertObject(o: E);

  def updateObject(o: E);

  def deleteObject(o: E);

}