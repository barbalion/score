package score.xml

import score.meta.Entity
import score.meta.fields.Field

trait XmlSerializable extends Entity {
  def xmlName: String = null
}

object XmlSerializable {
  def serializableFields(fields: Seq[Field[Entity, Any]]) = fields map {
    case serializableField: XmlSerializableField[Entity, Any] => serializableField
    case field => sys.error("Field does not support xml serialization: %s" format field.toString)
  }
}
