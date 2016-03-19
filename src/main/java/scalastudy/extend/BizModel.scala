package scalastudy.extend

/**
  * Created by shuqin on 17/4/10.
  */
trait Person {
  def getId: String
  def getName: String
  def getAble: String
}

class Student(studentId: String, name:String, able:String) extends Person {
  override def getId: String = studentId
  override def getName: String = name
  override def getAble: String = able
}

class Teacher(teacherId: String, name:String, able:String) extends Person {
  override def getId: String = teacherId
  override def getName: String = name
  override def getAble: String = able
}
