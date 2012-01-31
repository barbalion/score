package score.meta

import util.matching.Regex
import java.io.{FileInputStream, File}
import xml.{Elem, XML}

class XmlStorage(objectContext: Context,
                              aFileMask: String,
                              elemSelector: Elem => Seq[Elem])
                             (implicit context: Context)
  extends Storage(context) with PreloadedStorage with ReadOnlyStorage
{

  val fileMask = aFileMask;

  def loadObject(o: Entity) {
    val found = (preloaded find (_ == o)).getOrElse({sys.error("Object not found: " + o);})
    o.assignFrom(found)
  }

  private def convertFileMaskToRegex(mask: String): String = {
    ("^" + mask.replaceAll("([\\.\\(\\)\\+])", "\\\\$1").replaceAllLiterally("*", ".*").replaceAllLiterally("?", ".") + "$") // replace wilcards
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
      val filteredFiles = files filter (x => (!x.isDirectory && (mask.findFirstIn(x.getName).isDefined)))
      (files filter (_.isDirectory) map (findFiles) foldLeft(filteredFiles)) (_ ++ _)
    }
    findFiles(root) filter (_.canRead)
  }

  protected def entityMetaFromElem(el: Elem) = {
    (objectContext.model.entities find (_.xmlName == el.label)).getOrElse(sys.error("Metadata for \"%s\" element not found." format (el.label)))
  }

  def createEntityFromElem(el: Elem) = {
    val meta = entityMetaFromElem(el)
    val o = meta.newObject(objectContext)
    meta.properties map (p => {
      if (p.isInstanceOf[XmlSerializableProperty[Entity, Any]])
        p.asInstanceOf[XmlSerializableProperty[Entity, Any]].setFromXml(o, el)
      else
        sys.error("Property does not support xml serialization: %s" format (p.toString))
    })
    o
  }

  override protected def objects() = synchronized({
    // create object stream for each file
    val xmlStreams = allFiles map (file => {
      // map elements to entities
      val xml = XML.load(new FileInputStream(file))
      val elements = if (elemSelector == null) xml :: Nil else elemSelector(xml)
      elements map (elem => createEntityFromElem(elem))
    })

    // concatenate all streams into one
    xmlStreams reduceLeft (_ ++ _)
  })
}
