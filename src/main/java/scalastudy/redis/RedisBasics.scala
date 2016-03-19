package scalastudy.redis

import redis.clients.jedis.Jedis

import scala.collection.immutable.Map
import scala.collection.immutable.HashMap

/**
  * Created by shuqin on 16/3/30.
  */
object RedisBasics extends App {

  launch()

  def launch(): Unit = {
    val jedis = new Jedis("localhost")
    println("Connection to server sucessfully")

    setMap(buildMap(), jedis)
    println("name exists: " + jedis.exists("name"))
    println("namex exists: " + jedis.exists("namex"))
  }

  def buildMap():Map[String,String] = {
    return HashMap("name"-> "shuqin", "age"->"30", "jiguan"->"hubei")
  }

  def setMap(map:Map[String,String], jedis: Jedis):Unit = {
    map.foreach { kv =>
      jedis.set(kv._1, kv._2)
    }
  }


}
