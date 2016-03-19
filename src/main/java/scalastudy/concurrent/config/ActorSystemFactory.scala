package scalastudy.concurrent.config

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
 * Created by lovesqcc on 16-4-3.
 */
object ActorSystemFactory {

  val config = ConfigFactory.parseString("""
    akka.loglevel = DEBUG
    akka.actor.debug {
      autoreceive = on
      lifecycle = on
    }
  """)

  val startDebugConfig = true

  def newInstance():ActorSystem = {
    if (startDebugConfig) {
      ActorSystem("actor-wordstat", config)
    }
    else {
      ActorSystem("actor-wordstat")
    }
  }

}
