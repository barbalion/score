package score.generator

import org.stringtemplate.v4._
import org.stringtemplate.v4.misc.ObjectModelAdaptor
import score.Context
import score.meta.Entity
import score.meta.std.fields.Ref

import scala.util.Try

class ScoreModelAdapter(implicit val context: Context) extends ObjectModelAdaptor {

  override def getProperty(interp: Interpreter, self: ST, o: scala.Any, property: scala.Any, propertyName: String): AnyRef = {
    o match {
      case s: String =>
        propertyName match {
          case "tokens" => scala.collection.JavaConversions.seqAsJavaList(s.split("( |_|\\-|\\.|\\,|\\;)+|(?<=[a-z])(?=[A-Z])"))
          case "toUpperCase" => s.toUpperCase(context.session.locale)
          case "toLowerCase" => s.toLowerCase(context.session.locale)
          case _ => super.getProperty(interp, self, o, property, propertyName)
        }
      case e: Entity => ScoreModelAdapter.convertValuesForST(Try(e.getClass.getMethod(propertyName).invoke(e)).getOrElse(null))
      case _ => super.getProperty(interp, self, o, property, propertyName)
    }
  }

}

object ScoreModelAdapter {
  def convertValuesForST(o: AnyRef): AnyRef = {
    o match {
      case s: Seq[_] => scala.collection.JavaConversions.seqAsJavaList(s)
      case m: Map[_, _] => scala.collection.JavaConversions.mapAsJavaMap(m)
      case r: Ref[_] => r()
      case other => other
    }
  }
}
