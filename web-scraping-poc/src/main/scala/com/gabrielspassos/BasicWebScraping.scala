package com.gabrielspassos

import org.jsonp.*

@main def getGoogle(): Unit = {
  val googlePage = Jsonp.connect("https://www.google.com").get()
  println(googlePage)
}