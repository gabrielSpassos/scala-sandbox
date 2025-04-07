package com.gabrielspassos

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration

@SpringBootApplication(
  //exclude = Array(classOf[MongoAutoConfiguration]),
)
@ComponentScan(Array("com.*"))
class ScalaApplication extends CommandLineRunner {
  override def run(args: String*): Unit = {}
}

object ApplicationStaticMain {
  def main(args: Array[String]): Unit = {
    run(classOf[ScalaApplication], args*)
  }
  
}