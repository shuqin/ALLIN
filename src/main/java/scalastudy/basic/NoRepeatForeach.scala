package scalastudy.basic

object Sex extends Enumeration {
  val Female = Value("Female")
  val Male = Value("Male")
  val Double = Value("Double")
}

class Person(var name:String, var age:Int, var ables:List[String], val sex:Sex.Value) {

  def setAge(age:Int):Unit = {
    this.age = age
  }

  def empty():String = { return "" }

  def getValue(fieldName:String):Any = {
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

object PersonsVisitor {

  /**
    * Map Processing Pattern
    * Fetch part fields from object list.
    */
  def getField(persons:List[Person], fieldName:String):List[Any] = {
    persons.map(p => p.getValue(fieldName))
  }

  /**
    * Filter Processing Pattern
    * Get some objects satisfy condition function from object list
    */
  def filter(persons:List[Person], accept:(Person => Boolean)): List[Person] = {
    persons.filter(accept)
  }

  /**
    * Aggregation Operation Pattern, eg. concat, sum
    */
  def aggregate[T](persons:List[Person], op: (Person => T), aggre: (List[T] => T)): T = {
    aggre(persons.map(op))
  }

  /**
    * If-Set Operation Pattern
    */
  def ifSet(persons:List[Person], accept:(Person=>Boolean), setFunc: (Person=>Unit)): List[Person] = {
    persons.foreach { p => if (accept(p)) { setFunc(p) } }
    persons
  }

  /**
    * If-Remove Operation Pattern
    */
  def ifRemove(persons:List[Person], accept:(Person=>Boolean)):Iterator[Person] = {
    persons.iterator.filter(p => ! accept(p) )
  }

  def buildPersons():List[Person] = {
    List( new Person("Lier", 20, List("Study", "Explore","Combination"), Sex.Male),
          new Person("lover", 16, List("Love","Chat","Combination"), Sex.Female),
          new Person("Tender", 18, List("Care", "Combination"), Sex.Double))

  }
}

object NoRepeatForeach extends App {

  launch()

  def launch():Unit = {
    val persons = PersonsVisitor.buildPersons()
    println(PersonsVisitor.getField(persons, "name"))
    println(PersonsVisitor.getField(persons, "ables"))
    println(PersonsVisitor.getField(persons, "sex"))
    println(PersonsVisitor.getField(persons, "none"))

    PersonsVisitor.filter(persons, p => p.ables.contains("Care")).foreach { println _ }

    println("All ables: " + PersonsVisitor.aggregate(persons, p=>p.ables.mkString(","), (ablelist:List[String]) => ablelist.toSet.mkString(",")))
    println("Total age: " + PersonsVisitor.aggregate(persons, p=>p.age, (agelist:List[Int]) => agelist.sum))
    println("If-Set:" + PersonsVisitor.ifSet(persons, p=> p.age >= 18, p=> p.setAge(p.age+1) ))
    println("If-Remove: " + PersonsVisitor.ifRemove(persons, p=> p.age >= 18).toList)
  }

}
