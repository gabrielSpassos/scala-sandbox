package com.gabrielspassos

import org.jsoup.*

@main def getGoogle(): Unit = {
  val googlePage = Jsoup.connect("https://www.google.com").get()
  println(googlePage)
}