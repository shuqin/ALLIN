package scalastudy.concurrent.actors

import akka.actor.{PoisonPill, Actor, ActorRef}

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

  override def receive: Receive = {
    case filename:String =>

      ReadFileActor.inc()
      println("File count: " + ReadFileActor.count())
      println(filename)

      val content = readFile(filename)
      analysisWordActor ! content
    case PoisonPill =>
      analysisWordActor ! PoisonPill
      context.stop(self)
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
