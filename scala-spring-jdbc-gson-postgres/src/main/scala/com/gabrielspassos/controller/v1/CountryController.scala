package com.gabrielspassos.controller.v1

import com.gabrielspassos.dto.CountryDTO
import com.gabrielspassos.service.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1/countries"))
class CountryController @Autowired()(private val countryService: CountryService) {

  @GetMapping(Array("/{name}"))
  def findByCode(@PathVariable(name = "name", required = true) name: String): ResponseEntity[List[CountryDTO]] = {
    val countries = countryService.getCountryByName(name)
    ResponseEntity.ok(countries)
  }

  @GetMapping
  def findAll(): ResponseEntity[List[CountryDTO]] = {
    val countries = countryService.getAllCountries
    ResponseEntity.ok(countries)
  }
}
