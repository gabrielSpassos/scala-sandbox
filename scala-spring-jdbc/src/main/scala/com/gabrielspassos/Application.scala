package com.gabrielspassos

import com.gabrielspassos.dao.{BankDAO, CardDAO}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application @Autowired()(private val cardDAO: CardDAO, private val bankDAO: BankDAO) {

  @Bean
  def commandLineRunner(): CommandLineRunner = new CommandLineRunner {
    override def run(args: String*): Unit = {
      println("Application started with command-line arguments: " + args.mkString(", "))
      cardDAO.insertBasicData()
      bankDAO.insertBasicData()
    }
  }

}

object Application {
  def main(args: Array[String]): Unit = {
    run(classOf[Application], args*)
  }
}