package scalastudy.concurrent.wordstat

import java.io.File

import akka.actor.{Actor, ActorRef, PoisonPill}
import akka.event.Logging

import scala.collection.mutable.ArrayBuffer
import scalastudy.concurrent.ActorTerminationMsg

/**
 * Created by lovesqcc on 16-4-2.
 */
class FetchJavaFileActor(readFileActor: ActorRef) extends Actor {

  val log = Logging(context.system, self)

  override def receive: Actor.Receive = {
    case path:String =>
      val allJavaFiles:Array[String] = fetchAllJavaFiles(path)
      allJavaFiles.foreach {
        readFileActor ! _
      }
      readFileActor ! ActorTerminationMsg
      context.stop(self)

    case _ =>
      log.info("Unknown received.")
  }

  def fileJavaFile(filename:String, suffix:String): Boolean = {
    return filename.endsWith(suffix)
  }

  def fetchAllJavaFiles(path:String): Array[String] = {
    val javaFilesBuf = ArrayBuffer[String]()
    fetchJavaFiles(path, javaFilesBuf)
    return javaFilesBuf.toArray
  }

  def fetchJavaFiles(path:String, javafiles:ArrayBuffer[String]):Unit = {
    val dirAndfiles = new File(path).listFiles
    if (dirAndfiles == null) {
      log.info("{} not found.", path)
      readFileActor ! PoisonPill
      context.stop(self)
    }
    if (dirAndfiles != null && dirAndfiles.length > 0) {
      val files = dirAndfiles.filter(_.isFile)
      if (files.length > 0) {
        javafiles ++= files.map(_.getCanonicalPath).filter(fileJavaFile(_,".java"))
      }

      val dirs = dirAndfiles.filter(_.isDirectory)
      if (dirs.length > 0) {
        dirs.map(_.getCanonicalPath).foreach { dirpath =>
          fetchJavaFiles(dirpath, javafiles) }
      }
    }
  }
}
