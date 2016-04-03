package scalastudy.concurrent.actors

import akka.actor.{PoisonPill, Actor, ActorRef}
import akka.event.Logging

import scalastudy.concurrent.ActorTerminationMsg

/**
 * Created by lovesqcc on 16-4-2.
 */
// 记录读取的文件数便于核对
object ReadFileActor {
  private var fileCount = 0
  private def inc() { fileCount +=1 }
  private def count() = fileCount
}

class ReadFileActor(analysisWordActor: ActorRef) extends Actor {

  val log = Logging(context.system, self)

  override def receive: Receive = {
    case filename:String =>

      ReadFileActor.inc()
      log.info("file {} received.", filename)

      val content = readFile(filename)
      analysisWordActor ! content

    case ActorTerminationMsg =>
      log.info("File count: " + ReadFileActor.count())
      analysisWordActor ! ActorTerminationMsg

    case _ =>
      log.info("Unknown received.")
  }

  def readFile(filename:String): String = {
    import scala.io.Source
    val fileSource =  Source.fromFile(filename)
    try {
      return fileSource.mkString
    } finally {
      fileSource.close()
    }
  }
}
