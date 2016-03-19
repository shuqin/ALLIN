package traits

import java.io.File

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scalastudy.traits.LineHandler

/**
 * Created by lovesqcc on 16-3-27.
 */
trait FileAbility extends LineHandler {

  def readFile(filename:String): String = {
    val fileSource =  Source.fromFile(filename)
    try {
      return fileSource.mkString
    } finally {
      fileSource.close()
    }
  }

  def readFileLines(filename:String):List[String] = {
    val fileSource =  Source.fromFile(filename)
    try {
      return fileSource.getLines().toList
    } finally {
      fileSource.close()
    }
  }

  /* a simple tool for processing a file */
  def handleFile(filePathHandler:(String) => String)
                (fileContentHandler: (String) => Any)
                (filepath: String): Any = {
    return fileContentHandler(filePathHandler(filepath))
  }

  /* a simple frame for processing files */
  def
  handleFiles(filePathHandler:(String) => List[String])
                 (fileFilterHandler: (String) => Boolean)
                 (fileHandlerList: List[(String)=>List[Any]])
                 (totalHandler: List[Any] => Any)
                 (filepath:String): Any = {
    return totalHandler(
      fileHandlerList.map(
        (handle:(String)=>List[Any]) =>
          filePathHandler(filepath).filter(fileFilterHandler(_))
            .map(handle(_)).flatten
      )
    )
  }

  def findInFile(text:String):Any = {
    val patt = "f\\w+".r
    return patt.findAllIn(text).toList
  }

  def countInFile(text:String):Any = {
    return text.split("\n").toList.filter(s => ! s.matches("^\\s*$")).length
  }

  def fetchAllFiles(path:String): List[String] = {
    val fetchedFilesBuf = ArrayBuffer[String]()
    fetchFiles(path, fetchedFilesBuf)
    return fetchedFilesBuf.toList
  }

  def fetchFiles(path:String, fetchedFiles:ArrayBuffer[String]):Unit = {
    val dirAndfiles = new File(path).listFiles
    if (dirAndfiles!=null && dirAndfiles.length > 0) {
      val files = dirAndfiles.filter(_.isFile)
      if (files.length > 0) {
        fetchedFiles ++= files.map(_.getCanonicalPath)
      }

      val dirs = dirAndfiles.filter(_.isDirectory)
      if (dirs.length > 0) {
        dirs.map(_.getCanonicalPath).foreach { dirpath =>
          fetchFiles(dirpath, fetchedFiles) }
      }
    }
  }


  def handleFile(filename:String):List[Any] = {
    return readFileLines(filename).map(handle(_)).toList
  }

  def handleFileWithNoReturn(filename:String, lineHandler: LineHandler):Unit = {
    readFileLines(filename).foreach { line =>
      lineHandler.handle(line)
    }
  }

  def extraFilename(filepath:String):String = {
    val ind = filepath.lastIndexOf('/')
    if (ind == -1) {
      return filepath
    }
    else {
      return filepath.substring(ind + 1);
    }
  }

  def mkdir(path:String):Boolean = {
    val file = new File(path)
    if (!file.exists) {
      return file.mkdirs
    }
    return true
  }

  def testExtraFilename(): Unit = {
    val filepath = "/home/lovesqcc/work/java/ALLIN/src/main/java/cc/lovesq/dao/CreativeDAO.java"
    assert(extraFilename(filepath) == "CreativeDAO.java")
    val filepath2 = "CreativeDAO.java"
    assert(extraFilename(filepath2) == "CreativeDAO.java")
    println("test ExtraFilename passed.")
  }

}
