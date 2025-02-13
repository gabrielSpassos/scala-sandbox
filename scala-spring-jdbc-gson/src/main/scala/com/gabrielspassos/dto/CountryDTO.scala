package com.gabrielspassos.dto

import com.gabrielspassos.client.response.{CountryNameResponse, CountryResponse}

case class CountryDTO(
  name: CountryNameDTO,
  cca2: String,
  ccn3: String,
  cca3: String,
  cioc: String,
  independent: Boolean,
  status: String,
  unMember: Boolean,
  capital: List[String]
)

case class CountryNameDTO(
  common: String,
  official: String,
)

object CountryDTO {
  def toDTO(countryResponse: CountryResponse): CountryDTO = {
    CountryDTO(
      name = toCountryNameDTO(countryResponse.name),
      cca2 = countryResponse.cca2,
      ccn3 = countryResponse.ccn3,
      cca3 = countryResponse.cca3,
      cioc = countryResponse.cioc,
      independent = countryResponse.independent,
      status = countryResponse.status,
      unMember = countryResponse.unMember,
      capital = countryResponse.capital
    )
  }
  
  private def toCountryNameDTO(countryNameResponse: CountryNameResponse): CountryNameDTO = {
    CountryNameDTO(
      common = countryNameResponse.common,
      official = countryNameResponse.official,
    )
  }
}
