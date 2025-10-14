import net.rubyeye.xmemcached.XMemcachedClientBuilder
import net.rubyeye.xmemcached.utils.AddrUtil

@main def memcachedPoc(): Unit = {
  // Connect to Memcached running on localhost:11211
  val builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211"))
  val client = builder.build()

  try {
    val result = client.get[String]("greeting-poc")
    if (null != result) {
      println(s"Value from Memcached: $result")
      return
    }

    // Set a value with key "greeting" for 60 seconds
    client.set("greeting-poc", 60, "Hello from Scala 3 + XMemcached!")
    val result2 = client.get[String]("greeting-poc")
    println(s"Value from Memcached after set: $result2")
  } finally {
    client.shutdown()
  }
}
