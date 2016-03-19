package scalastudy.extend

/**
  * Created by shuqin on 17/4/10.
  */
object ExtendedReport extends App {
  launch()

  def launch(): Unit = {
    report(List("Id", "Name"), getPersons())
    report(List("Name", "Able"), getPersons())
  }

  def report(fields:List[String], persons:List[Person]):Unit = {
    val titles = fields.map(FieldConf(_).getTitle).mkString(",")
    println(titles)
    val rows = persons.map(
      p => fields.map(FieldConf(_).getAble.apply(p)).mkString(",")
    ).mkString("\n")
    println(rows)
  }

  def getPersons():List[Person] = {
    List(new Student("s1", "liming", "Study"), new Student("s2", "xueying", "Piano"),
         new Teacher("t1", "Mr.Q", "Swim"), new Teacher("t2", "Mrs.L", "Dance"))
  }

}







