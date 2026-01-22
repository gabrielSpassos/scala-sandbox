package com.gabrielspassos.config

import org.apache.hc.core5.http.HttpHost
import org.opensearch.client.RestClient
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.transport.httpclient5.{ApacheHttpClient5Transport, ApacheHttpClient5TransportBuilder}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

import java.net.URI

@Configuration
class OpenSearchConfig @Autowired()(gsonJsonpMapper: GsonJsonpMapper) {

  @Bean
  def openSearchClient(): OpenSearchClient = {
    val transport = ApacheHttpClient5TransportBuilder
        .builder(new HttpHost("http", "localhost", 9200))
        .setMapper(gsonJsonpMapper)
        .build()

    new OpenSearchClient(transport)
  }
}
