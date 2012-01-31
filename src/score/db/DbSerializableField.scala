package score.db

import java.sql.ResultSet
import score.meta._
import score.meta.fields.Field

trait DbSerializableField[E <: Entity, T] extends Field[E, T] {
  def setFromDb(o: E, rs: ResultSet): T
}
