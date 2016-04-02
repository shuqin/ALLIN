package scalastudy.traits

/**
 * Created by lovesqcc on 16-4-2.
 */
trait LinePrintHandler extends LineHandler {
  override def handle(line: String): Any = {
    println(line)
  }
}
