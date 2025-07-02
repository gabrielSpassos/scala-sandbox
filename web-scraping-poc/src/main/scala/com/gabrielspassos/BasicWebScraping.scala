package com.gabrielspassos

import org.jsoup.*

@main def getGoogle(): Unit = {
  val googlePageDoc = Jsoup.connect("https://www.google.com").get()
  println(googlePageDoc.title())
  println(googlePageDoc.location())
  println(googlePageDoc.head())
}

@main def selectElements(): Unit = {
  val wikipediaPageDoc = Jsoup.connect("https://en.wikipedia.org/").get()

  val inTheNews = wikipediaPageDoc.select("#mp-itn b a")
  println("In the News:")
  println(inTheNews)
  println()

  val onThisDay = wikipediaPageDoc.select("#mp-otd b a")
  println("On this Day:")
  println(onThisDay)
  println()

  val didYouKnow = wikipediaPageDoc.select("#mp-dyk b a")
  println("Did you know:")
  println(onThisDay)
  println()
}