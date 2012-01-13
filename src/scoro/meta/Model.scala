package scoro.meta

class Model {
  lazy val entities = foldEntities();
  var f: Map[String, String] = null;
  f = f + (("a", "b"))

  def foldEntities(): List[EntityMeta[Entity]] = null // todo
}