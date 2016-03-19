package scalastudy.traits

/**
 * Created by lovesqcc on 16-4-2.
 */
trait LineNumberHandler extends LineHandler {
  var id = 0
  override def handle(line:String): Any = {
    id += 1
    //println("number: " + line);
    return id + ": " + line
  }
}
