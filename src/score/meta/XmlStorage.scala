package scoro.meta

import collection.immutable.Stream
import util.matching.Regex
import java.io.{FileInputStream, File}
import xml.{Elem, XML}

class XmlStorage[E <: Entity](objectContext: Context[E], aFileMask: String, elemSelector: Elem => Seq[Elem])(implicit context: Context[Storage[E]])
  extends Storage[E](objectContext) with PreloadedStorage[E] with ReadOnlyStorage[E] {

  val fileMask = aFileMask;

  override def loadObject(o: E) {
    val found = (preloaded find (_ == o)).getOrElse({sys.error("Object not found: " + o);})
    o.assignFrom(found)
  }

  private def convertFileMaskToRegex(mask: String): String = {
    ("^" + mask.replaceAll("[\\.\\(\\)\\+]", "\\$1").replaceAll("*", ".*").replaceAll("?", ".") + "$") // replace wilcards
      .replaceAllLiterally("^.*", "").replaceAllLiterally(".*$", "") // remove ignorant sequences
  }

  private def allFiles: Array[File] = {
    // prepare search params
    val f: File = new File(fileMask)
    val root = if (f.isDirectory) f else new File(f.getPath)
    val mask = new Regex(if (f.isDirectory) "\\.xml$" else convertFileMaskToRegex(f.getName))

    // find all files
    def findFiles(f: File): Array[File] = {
      val files = f.listFiles()
      files filter (_.isDirectory) map (findFiles) reduceLeft (_ ++ _)
      files filter (x => (!x.isDirectory && (mask.findFirstIn(x.getName).isDefined)))
    }
    findFiles(root) filter (_.canRead)
  }

  protected def createEntityFromElem(el: Elem) = {
    val metaO = (context.model.entities find (_.xmlName == el.label))
    if (metaO.isEmpty)
      sys.error("Metadata for \"%s\" element not found." format(el.label))
    val meta = metaO.get
    meta.newObjectFromXml(el)(context)
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
    xmlStreams map (_.asInstanceOf[Stream[E]]) reduceLeft (_ ++ _)
  })
}
