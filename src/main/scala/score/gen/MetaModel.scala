package score.gen

import score.gen._

object MetaModel {
  // init model
  Seq(
    score.gen.meta.NameKeyEntityMeta,
    score.gen.meta.MetaObjectMeta,
    score.gen.meta.EntityMetaMeta,
    score.gen.meta.FieldMetaMeta,
    score.gen.meta.fields.FieldMeta,
    score.gen.meta.fields.SimpleValueFieldMeta,
    score.gen.meta.fields.ComparableSimpleValueFieldMeta,
    score.gen.meta.std.fields.StringFieldMeta,
    score.gen.meta.std.fields.BooleanFieldMeta,
    score.gen.meta.std.fields.IntegerFieldMeta,
    score.gen.meta.std.fields.RefFieldMeta,
    score.gen.meta.std.fields.ListFieldMeta
  ) foreach {
    _.init()
  }
}
