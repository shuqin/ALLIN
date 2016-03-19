package scalastudy.traits

/**
 * Created by lovesqcc on 16-4-2.
 */
trait LineSplitterHandler extends LineHandler {
  override def handle(line: String): Any = {
    return super.handle(line.split(":").mkString("(", ",", ")"))
  }
}
