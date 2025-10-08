package com.gabrielspassos.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(basePackages = Array("com.gabrielspassos.dao.cache.repository"))
@EnableJdbcRepositories(basePackages = Array("com.gabrielspassos.dao.repository"))
class DatabaseConfig
