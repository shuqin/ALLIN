package scalastudy.utils

import scala.collection.immutable.List

/**
 * Created by lovesqcc on 16-4-3.
 */
object StringUtil {

  def toString(obj:Any):String = {
     return obj.toString
  }

  def splitText(text:String, seps:Array[Char]): List[String] = {
    var init = Array(text)
    seps.foreach { sep =>
      init = init.map(_.split(sep)).flatten.map(_.trim).filter(s => s.length > 0)
    }
    return init.toList
  }

}
