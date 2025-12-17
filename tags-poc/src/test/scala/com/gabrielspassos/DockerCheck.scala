package com.gabrielspassos

import org.testcontainers.containers.PostgreSQLContainer


object DockerCheck extends App {
  val pg = new PostgreSQLContainer("postgres:latest")
  pg.start()
  println(pg.getJdbcUrl)
  pg.stop()
}
