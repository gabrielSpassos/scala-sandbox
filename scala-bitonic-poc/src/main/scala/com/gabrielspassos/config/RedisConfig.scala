package com.gabrielspassos.config

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig @Autowired(private val gson: Gson) {

  @Value("${spring.data.redis.host:localhost}")
  private val redisHost: String = "localhost"

  @Value("${spring.data.redis.port:6379}")
  private val redisPort: Int = 6379

  @Bean
  def redisConnectionFactory(): LettuceConnectionFactory = {
    new LettuceConnectionFactory(redisHost, redisPort)
  }

  @Bean
  def redisTemplate(): RedisTemplate[String, Any] = {
    val template = new RedisTemplate[String, Any]()
    template.setConnectionFactory(redisConnectionFactory())

    // Key serializer: keep keys as plain strings
    template.setKeySerializer(new StringRedisSerializer())
    template.setHashKeySerializer(new StringRedisSerializer())

//    // Value serializer: JSON for objects
//    val objectMapper = new ObjectMapper()
//    objectMapper.registerModule(DefaultScalaModule)
//    val jacksonSerializer = new Jackson2JsonRedisSerializer[Any](classOf[Object])
//    jacksonSerializer.setObjectMapper(objectMapper)
//
//    template.setValueSerializer(jacksonSerializer)
//    template.setHashValueSerializer(jacksonSerializer)

    template.afterPropertiesSet()
    template
  }
}
