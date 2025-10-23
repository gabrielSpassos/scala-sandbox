package com.gabrielspassos

import org.junit.jupiter.api.TestInstance
import org.springframework.test.context.{DynamicPropertyRegistry, DynamicPropertySource}
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class BaseIntegrationTest {
  
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
object BaseIntegrationTest {

  val postgresContainer: PostgreSQLContainer[?] = PostgreSQLContainer("postgres:latest")
  postgresContainer.withUsername("sa")
  postgresContainer.withPassword("test")
  postgresContainer.withDatabaseName("it-test-db")
  postgresContainer.withExposedPorts(5432)
  
  @DynamicPropertySource
  def configureProperties(registry: DynamicPropertyRegistry): Unit = {
    postgresContainer.start()

    registry.add("spring.datasource.url", () => postgresContainer.getJdbcUrl)
    registry.add("spring.datasource.username", () => postgresContainer.getUsername)
    registry.add("spring.datasource.password", () => postgresContainer.getPassword)
  }
}