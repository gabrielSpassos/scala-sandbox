package com.gabrielspassos.config

import org.springframework.context.annotation.{Bean, Configuration}

import java.net.Socket
import java.net.http.HttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.{SSLContext, SSLEngine, TrustManager, X509ExtendedTrustManager}

@Configuration
class HttpClientConfig {

  private val trustManager: TrustManager = new X509ExtendedTrustManager {
    override def checkClientTrusted(chain: Array[X509Certificate], authType: String, engine: SSLEngine): Unit = {}

    override def checkClientTrusted(chain: Array[X509Certificate], authType: String): Unit = {}

    override def checkClientTrusted(chain: Array[X509Certificate], authType: String, socket: Socket): Unit = {}

    override def checkServerTrusted(chain: Array[X509Certificate], authType: String, engine: SSLEngine): Unit = {}

    override def checkServerTrusted(chain: Array[X509Certificate], authType: String): Unit = {}

    override def getAcceptedIssuers: Array[X509Certificate] = Array()

    override def checkServerTrusted(chain: Array[X509Certificate], authType: String, socket: Socket): Unit = {}
  }
  
  @Bean
  def httpClient(): HttpClient = {
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, Array(trustManager), SecureRandom())

    HttpClient.newBuilder().sslContext(sslContext).build()
  }
}
