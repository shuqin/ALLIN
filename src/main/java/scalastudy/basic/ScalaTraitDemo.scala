package scalastudy.basic

import java.util

/**
 * Created by lovesqcc on 16-3-19.
 */
object ScalaTraitDemo extends App {

  launch()

  def launch(): Unit = {
      handleFile("/etc/passwd")
  }

  def handleFile(filename:String):Unit = {

    trait LinePrintHandler extends LineHandler {
      override def handle(line: String): Any = {
        println(line)
      }
    }

    val fileAbility = new FileAbility with LinePrintHandler
    fileAbility.handleFile(filename)


    trait LineSplitterHandler extends LineHandler {
      override def handle(line: String): Any = {
        return super.handle(line.split(":").mkString("(", ",", ")"))
      }
    }

    trait LineNumberHandler extends LineHandler {
      var id = 0
      override def handle(line:String): Any = {
        id += 1
        //println("number: " + line);
        return id + ": " + line
      }
    }

    val fileAbility2 = new FileAbility with LineNumberHandler with LineSplitterHandler
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
