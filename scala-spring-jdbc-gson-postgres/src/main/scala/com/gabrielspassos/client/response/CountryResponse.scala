package com.gabrielspassos.client.response

case class CountryResponse(
 name: CountryNameResponse,
 cca2: String,
 ccn3: String,
 cca3: String,
 cioc: String,
 independent: Boolean,
 status: String,
 unMember: Boolean,
 capital: List[String]
)

case class CountryNameResponse(
 common: String,
 official: String,
)