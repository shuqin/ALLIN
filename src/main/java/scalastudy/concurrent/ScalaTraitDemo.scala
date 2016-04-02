package scalastudy.concurrent

import scalastudy.traits.{LineSplitterHandler, LineNumberHandler, LinePrintHandler}
import scalastudy.utils.FileUtil

/**
 * Created by lovesqcc on 16-3-19.
 */
object ScalaTraitDemo extends App {

  launch()

  def launch(): Unit = {
    handleFile("/etc/passwd")
  }

  def handleFile(filename:String):Unit = {

    val fileAbility = new FileUtil with LinePrintHandler
    fileAbility.handleFile(filename)

    val fileAbility2 = new FileUtil with LineNumberHandler with LineSplitterHandler
    val result = fileAbility2.handleFile(filename)
    result.foreach { ref =>
      ref match {
        case s:String => println(s)
        case a:Array[Any] =>  for(e <- ref.asInstanceOf[Array[Any]]) {println(e)}
        case a:List[Any] =>  for(e <- ref.asInstanceOf[List[Any]]) {println(e)}
        case _ => println(ref)
      }
    }

  }

}

