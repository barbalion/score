package score.json

import score.meta.fields.Field

import util.parsing.json.JSONObject
import score.meta._

trait JsonSerializableField[E <: Entity, T] extends Field[E, T] {
  def setFromJson(o: E, jo: JSONObject): T
}

