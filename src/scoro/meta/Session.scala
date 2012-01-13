package scoro.meta

import java.util.UUID
import collection.mutable.HashMap

class Session {
  val guid = UUID.randomUUID();

  protected var cache = new HashMap[String, AnyRef];
}