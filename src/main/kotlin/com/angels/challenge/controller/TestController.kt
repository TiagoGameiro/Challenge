package com.angels.challenge.controller

import com.angels.challenge.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class TestController() {
    @Autowired
    var repository: ClusterRepository? = null
    @Autowired
    var repositoryRegions: RegionRepository? = null
    @Autowired
    var storeRepository: StoreRepository? = null
    @Autowired
    var productRepository: ProductRepository? = null
    @Autowired
    var seasonRepository: SeasonRepository? = null
    @Autowired
    var storeSeasonRepository: StoreSeasonRepository? = null

    @GetMapping("/clusters", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getClusters(): ResponseEntity<Any?> {
        val list = repository?.findAll()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/regions", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getRegions(): ResponseEntity<Any?> {
        val list = repositoryRegions?.findAll()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/stores", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getStores(): ResponseEntity<Any?> {
        val list = storeRepository?.findAll()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/products", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getProducts(): ResponseEntity<Any?> {
        val list = productRepository?.findAll()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/seasons", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getSeasons(): ResponseEntity<Any?> {
        val list = seasonRepository?.findAll()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/seasons/stores", produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getStoreSeasons(): ResponseEntity<Any?> {
        val list = storeSeasonRepository?.findAll()
        return ResponseEntity(list, HttpStatus.OK)
    }
}