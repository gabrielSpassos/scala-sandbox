package com.gabrielspassos.client

import com.gabrielspassos.client.response.CountryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

@Component
class CountryClient @Autowired()(val httpClient: HttpClient, val gson: Gson) {

  private val rootUrl = "https://restcountries.com"

  def getCountryByName(name: String): List[CountryResponse] = {
    val request = HttpRequest.newBuilder()
      .uri(URI.create(s"$rootUrl/v3.1/name/$name"))
      .header("Content-Type", "application/json")
      .header("Accept", "application/json")
      .GET()
      .build()

    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    val collectionType = new TypeToken[List[CountryResponse]]() {}.getType
    gson.fromJson(response.body(), collectionType)
  }

  def getAllCountries: List[CountryResponse] = {
    val request = HttpRequest.newBuilder()
      .uri(URI.create(s"$rootUrl/v3.1/all"))
      .header("Content-Type", "application/json")
      .header("Accept", "application/json")
      .GET()
      .build()

    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    val collectionType = new TypeToken[List[CountryResponse]]() {}.getType
    gson.fromJson(response.body(), collectionType)
  }

}
