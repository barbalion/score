package scoro.meta

import xml.Elem

class Model {
  protected var fEntities: List[EntityMeta[Entity]] = Nil;
  protected var fStorages: List[Storage[Entity]] = Nil;
  protected var fFields: List[Storage[Entity]] = Nil;

  def entities: List[EntityMeta[Entity]] = fEntities
}

object Model {
  def loadFromCatalog(catalogFile: String): Model = {
    val xmlStorage = new XmlStorage[MetaObject](RunTimeModelContext, catalogFile, el => el.child map (_.asInstanceOf[Elem]))(SystemContext)
    loadFromStorage(xmlStorage);
  }

  def loadFromStorage(storage: Storage[MetaObject]): Model = {
    val model = new Model();
    storage.list(null, null, null) map ((o) => {o match {
      case e: EntityMeta[Entity] => model.fEntities = e :: model.fEntities
      case other => sys.error("Not supported object in model: " + other)
    }})
    model
  }
}

object RunTimeModel extends Model {
  def registerEntity[E <: Entity](meta: EntityMeta[E]) {
    fEntities = meta.asInstanceOf[EntityMeta[Entity]] :: fEntities
  }
}

object RunTimeModelStorage extends Storage[MetaObject](RunTimeModelContext)(SystemContext) with WriteOnlyStorage[MetaObject] {
  def deleteObject(o: MetaObject) = null

  def insertObject(o: MetaObject) = null

  def updateObject(o: MetaObject) = null
}
