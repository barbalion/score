package score.db

import score.meta.Entity

trait DbSerializable extends Entity {
  var sqlName: String = null;// = MetaObject.formatSqlName(name);
}






