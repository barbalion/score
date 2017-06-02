package score

import collection.mutable.HashMap
import java.util.{Locale, UUID}

class Session {
  var locale = Locale.getDefault;

  val guid = UUID.randomUUID();

  protected var cache = new HashMap[String, AnyRef];
}

object SystemSession extends Session {

}