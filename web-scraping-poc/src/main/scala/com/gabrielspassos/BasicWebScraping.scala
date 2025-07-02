package com.gabrielspassos

import org.jsoup.*

@main def getGoogle(): Unit = {
  val googlePageDoc = Jsoup.connect("https://www.google.com").get()
  println(googlePageDoc.title())
  println(googlePageDoc.location())
  println(googlePageDoc.head())
}