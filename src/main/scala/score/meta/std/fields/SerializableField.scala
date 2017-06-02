package score.meta.fields

import score.xml.XmlSerializableField
import score.meta._
import score.json.JsonSerializableField
import score.db.DbSerializableField

trait SerializableField[E <: Entity, T] extends XmlSerializableField[E, T] with JsonSerializableField[E, T] with DbSerializableField[E, T] {
}

