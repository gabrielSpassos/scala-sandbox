package com.gabrielspassos.service

import com.gabrielspassos.client.CountryClient
import com.gabrielspassos.dto.CountryDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryService @Autowired()(val countryClient: CountryClient) {

  def getCountryByName(name: String): List[CountryDTO] = {
    countryClient.getCountryByName(name).map(CountryDTO.toDTO)
  }

  def getAllCountries: List[CountryDTO] = {
    countryClient.getAllCountries.map(CountryDTO.toDTO)
  }
  
}
