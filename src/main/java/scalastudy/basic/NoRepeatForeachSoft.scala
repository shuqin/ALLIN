package scalastudy.basic

/**
  * Created by shuqin on 17/4/24.
  */
class Person3(var name:String, var age:Int, var ables:List[String], val sex:Sex.Value) extends FieldValue {

  def setAge(age:Int):Unit = {
    this.age = age
  }

  def empty():String = { return "" }

  override def getValue(fieldName:String):Any = {
    fieldName match {
      case "name" => name
      case "age" => age
      case "ables" => ables
      case "sex" => sex
      case _ => empty
    }
  }

  override def toString = {
    s"${this.name} is ${this.sex} sex , ${this.age} years old, able to do : " + this.ables.mkString("'",",", "'")
  }
}

object Visitor3 {

  /**
    * Map Processing Pattern
    * Fetch part fields from object list.
    */
  def getField[T <: FieldValue](objs:List[T], fieldName:String):List[Any] = {
    objs.map(p => p.getValue(fieldName))
  }

  /**
    * Filter Processing Pattern
    * Get some objects satisfy condition function from object list
    */
  def filter[T](objs:List[T])(accept:(T => Boolean)): List[T] = {
    objs.filter(accept)
  }

  /**
    * Aggregation Operation Pattern, eg. concat, sum
    */
  def aggregate[R,T](objs:List[R])(op: (R => T))(aggre: (List[T] => Any)): Any = {
    aggre(objs.map(op))
  }

  /**
    * If-Set Operation Pattern
    */
  def ifSet[T](objs:List[T])(accept:(T=>Boolean))(setFunc: (T=>Unit)): List[T] = {
    objs.foreach { p => if (accept(p)) { setFunc(p) } }
    objs
  }

  /**
    * If-Remove Operation Pattern
    */
  def ifRemove[T](objs:List[T])(accept:(T=>Boolean)):Iterator[T] = {
    objs.iterator.filter(p => ! accept(p) )
  }

  def buildPersons():List[Person3] = {
    List( new Person3("Lier", 20, List("Study", "Explore","Combination"), Sex.Male),
          new Person3("lover", 16, List("Love","Chat","Combination"), Sex.Female),
          new Person3("Tender", 18, List("Care", "Combination"), Sex.Double))

  }
}

object NoRepeatForeachSoft extends App {

  launch()

  def launch():Unit = {
    val persons = Visitor3.buildPersons()
    println(Visitor3.getField(persons, "name"))
    println(Visitor3.getField(persons, "ables"))
    println(Visitor3.getField(persons, "sex"))
    println(Visitor3.getField(persons, "none"))

    Visitor3.filter(persons)(p => p.ables.contains("Care")).foreach { println _ }

    println("All ables: " + Visitor3.aggregate(persons)(p=>p.ables.mkString(","))(ablelist => ablelist.toSet.mkString(",")))
    println("Total age: " + Visitor3.aggregate(persons)(p=>p.age)(agelist => agelist.sum))
    println("If-Set:" + Visitor3.ifSet(persons)(p=> p.age >= 18)(p=> p.setAge(p.age+1)))
    println("If-Remove: " + Visitor3.ifRemove(persons)(p=> p.age >= 18).toList)
  }

}
