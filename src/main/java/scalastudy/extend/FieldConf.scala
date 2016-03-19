package scalastudy.extend

/**
  * Created by shuqin on 17/4/10.
  * Scala 实现枚举; 由于 Enumeration 不支持枚举含有方法字段，因此采用样例对象模拟实现
  */
sealed class FieldConf(name:String, title:String, able: (Person)=>String) {
  def getTitle = title
  def getAble = able
}

object FieldConf {

  def apply(name: String): FieldConf = {
    name match {
      case "Id" => Id
      case "Name" => Name
      case "Able" => Able
      case _ => Unknown
    }
  }
}

case object Id extends FieldConf("Id", "编号", p => p.getId)
case object Name extends FieldConf("Name", "姓名", p => p.getName)
case object Able extends FieldConf("Able", "能力", p => p.getAble)
case object Unknown extends FieldConf("Unknown", "未知", p => "")
