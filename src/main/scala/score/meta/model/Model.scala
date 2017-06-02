package score.meta.model

import score.SystemContext
import score.gen.meta.{EntityMetaMeta, NameKeyEntity}
import score.meta._
import score.storage.Storage
import score.xml._

import scala.xml.Elem

class Model(protected var aEntities: List[EntityMeta]) {

  def entities = aEntities
}

object Model {

  def loadFromCatalog(catalogFile: String): Model = {
    loadFromStorage(new XmlStorage(CompiledModelContext, catalogFile, el => el \\ "Model" \ "_" map (_.asInstanceOf[Elem]))(SystemContext))
  }

  def loadFromStorage(storage: Storage): Model = new Model((storage.list(EntityMetaMeta, null, null) map (_.asInstanceOf[EntityMeta])).toList)
}

class BadMetaModel() extends Exception("Not implemented yet.") {
}

object BadMetaModel {
  def apply(e: NameKeyEntity, message: String) = throw new ModelException("Bad Model for \"%s\": %s: " format(e.name, message))
}