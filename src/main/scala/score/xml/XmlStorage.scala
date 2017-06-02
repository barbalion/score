package score.xml

import java.io.{File, FileInputStream}

import score.Context
import score.meta._
import score.storage.{PreloadedStorage, ReadOnlyStorage, Storage}

import scala.util.matching.Regex
import scala.xml.{Elem, XML}

class XmlStorage(objectContext: Context, aFileMask: String, elemSelector: Elem => Seq[Elem])(implicit context: Context)
  extends Storage(context) with PreloadedStorage with ReadOnlyStorage {

  val fileMask = aFileMask

  def loadObject(o: Entity) {
    o.assignFrom((preloadedObjects find (_ == o)).getOrElse({
      sys.error("Object not found: " + o)
    }))
  }

  private def convertFileMaskToRegex(mask: String): String = {
    ("^" + mask.replaceAll("([\\.\\(\\)\\+])", "\\\\$1").replaceAllLiterally("*", ".*").replaceAllLiterally("?", ".") + "$") // replace wildcards
      .replaceAllLiterally("^.*", "").replaceAllLiterally(".*$", "") // remove ignorant sequences
  }

  private def allFiles: Array[File] = {
    // prepare search params
    val f: File = new File(fileMask)
    val root = if (f.isDirectory) f else new File(if (f.getParent == null) "." else f.getParent)
    val mask = new Regex(if (f.isDirectory) "\\.xml$" else convertFileMaskToRegex(f.getName))

    // find all files
    def findFiles(f: File): Array[File] = {
      val files = f.listFiles()
      val filteredFiles = files filter (x => !x.isDirectory && mask.findFirstIn(x.getName).isDefined)
      (files filter (_.isDirectory) map findFiles foldLeft filteredFiles)(_ ++ _)
    }
    findFiles(root) filter (_.canRead)
  }

  override protected def objects() = synchronized({
    // create object stream for each file
    val xmlStreams = allFiles map (file => {
      // map elements to entities
      val xml = XML.load(new FileInputStream(file))
      val elements = if (elemSelector == null) xml :: Nil else elemSelector(xml)
      elements map (elem => XmlStorage.createObjectFromXml(elem, objectContext))
    })

    // concatenate all streams into one
    xmlStreams reduceLeft (_ ++ _)
  })
}

object XmlStorage {
  def entityMetaFromElem(el: Elem, objectContext: Context) = {
    (objectContext.model.entities find { e => e.xmlName == el.label }).getOrElse(
      sys.error("Metadata for \"%s\" element not found." format el.label)) match {
      case meta: XmlSerializableEntityMeta => meta
      case other => sys.error("Entity does not support xml serialization: %s" format other)
    }
  }

  def createObjectFromXml(el: Elem, objectContext: Context) = {
    entityMetaFromElem(el, objectContext).newObjectFromXml(el, objectContext).save()
  }

}