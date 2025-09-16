package com.gabrielspassos.config

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EnableJdbcRepositories
@EnableRedisRepositories
class DatabaseConfig
