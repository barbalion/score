package scoro.meta

import xml.Elem

abstract class EntityMeta[E <: Entity](implicit aContext: Context[EntityMeta[E]]) extends MetaObject[EntityMeta[E]] {
  var fields: List[FieldMeta[E, Nothing]] = null;

  def newObject(implicit aContext: Context[E]): E;

  def newObjectFromXml(el: Elem)(implicit aContext: Context[E]) = {
    val o = newObject

    o
  }

  def key = fields filter (_.isKey)

//  def this(name: String) {
//    this();
//  }
}