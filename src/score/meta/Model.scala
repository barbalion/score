package score.meta

import xml.Elem

class Model {
  protected var fEntities: List[EntityMeta[Entity]] = Nil;
  protected var fStorages: List[Storage] = Nil;
  protected var fProperties: List[EntityProperty[Entity, Any]] = Nil;

  def entities = fEntities
}

object Model {
  def loadFromCatalog(catalogFile: String): Model = {
    val xmlStorage = new XmlStorage(CompiledModelContext, catalogFile, el => el \\ "Model" \ "_" map (_.asInstanceOf[Elem]))(SystemContext)
    loadFromStorage(xmlStorage);
  }

  def loadFromStorage(storage: Storage): Model = {
    val model = new Model();
    storage.list(EntityMetaMeta, null, null) map ((o) => {o match {
      case e: EntityMeta[Entity] => model.fEntities = e.asInstanceOf[EntityMeta[Entity]] :: model.fEntities
      case other => sys.error("Not supported object in model: " + other)
    }})
    model
  }
}

object CompiledModel extends Model {
  def registerEntity[E <: Entity](meta: EntityMeta[E]) {
    fEntities = meta.asInstanceOf[EntityMeta[Entity]] :: fEntities
  }
}

object CompiledModelStorage extends Storage(CompiledModelContext)(SystemContext) with WriteOnlyStorage {
  def deleteObject(o: Entity) = null //todo

  def insertObject(o: Entity) = null

  def updateObject(o: Entity) = null
}
