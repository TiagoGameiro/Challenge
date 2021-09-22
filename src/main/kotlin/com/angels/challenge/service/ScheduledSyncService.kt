package com.angels.challenge.service

import com.angels.challenge.entity.*
import com.angels.challenge.model.*
import com.angels.challenge.repository.*
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.modelmapper.ModelMapper
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors


@Service
class ScheduledSyncService(
    val fetchDataService: FetchDataService,
    val clusterRepository: ClusterRepository,
    val regionRepository: RegionRepository,
    val storeRepository: StoreRepository,
    val productRepository: ProductRepository,
    val seasonRepository: SeasonRepository,
    val storeSeasonRepository: StoreSeasonRepository,
) {
    private val modelMapper = ModelMapper()
    final inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    fun startSyncProcess() {
        syncAllClusters()
        syncAllRegions()
        syncAllStores()
        syncAllProducts()
        syncStoreProducts()
    }

    fun syncAllClusters() {
        val clusters = fetchDataService.getAllPaginatedData<Cluster>(0, "/clusters")
        val listType = object : TypeToken<List<ClusterEntity?>?>() {}.type

        val entityList: List<ClusterEntity> = modelMapper.map(clusters, listType)
        clusterRepository.saveAll(entityList)
    }

    fun syncAllRegions() {
        val regions = fetchDataService.getAllPaginatedData<Region>(0, "/regions")
        val listType = object : TypeToken<List<RegionEntity?>?>() {}.type

        val entityList: List<RegionEntity> = modelMapper.map(regions, listType)
        for(regionEntity in entityList) {
            regionEntity.cluster = regionEntity.clusters?.let { clusterRepository.findByName(it) }
        }
        regionRepository.saveAll(entityList)
    }

    fun syncAllStores() {
        val stores = fetchDataService.getAllPaginatedData<Store>(0, "/stores")
        val listType = object : TypeToken<List<StoreEntity?>?>() {}.type

        val entityList: List<StoreEntity> = modelMapper.map(stores, listType)
        for(storeEntity in entityList) {
            storeEntity.relatedRegion = storeEntity.region.let { regionRepository.findByName(it) }
        }
        storeRepository.saveAll(entityList)
    }

    fun syncAllProducts() {
        val productsAsCSV = fetchDataService.getData<String>("/product-data.csv", HttpMethod.GET, "", typeReference()).block()

        val schema = CsvSchema.emptySchema().withHeader()
        val mapper: ObjectReader = CsvMapper().reader(Product::class.java).with(schema)

        val productsList: MutableList<Product> = listOf<Product>().toMutableList()

        val it: MappingIterator<Product> = mapper.readValues(productsAsCSV)
        while (it.hasNext()) {
            productsList.add(it.next())
        }

        val listType = object : TypeToken<List<ProductEntity?>?>() {}.type
        val entityList: List<ProductEntity> = modelMapper.map(productsList, listType)
        productRepository.saveAll(entityList)
    }

    fun syncStoreProducts() {
        CoroutineScope(Dispatchers.Default).launch {
            //synchronizedList is a thread-safe list so that multiple threads trying to write on it dont mess it up
            val arrayProducts = Collections.synchronizedList(mutableListOf<StoreProduct>())

            coroutineScope {
                for(i in 0..19000) {
                    launch {
                        fetchDataService.getStoreProductPerPage(i, "stores/products").map {
                            arrayProducts.add(it)
                        }
                    }
                }
            }

            relateProducts(arrayProducts)
        }
    }

    fun relateProducts(arrayProducts: MutableList<StoreProduct>) {
        val storeMap: Map<String, StoreEntity> = storeRepository.findAll().stream().collect(Collectors.toMap(StoreEntity::name) { it })
        val seasonMap: Map<String, SeasonEntity> = seasonRepository.findAll().stream().collect(Collectors.toMap(SeasonEntity::id) { it })
        val productMap: Map<String, ProductEntity> = productRepository.findAll().stream().collect(Collectors.toMap(ProductEntity::sku) { it })
        val storeSeasonEntityList = mutableListOf<StoreSeasonEntity>()

        for(product in arrayProducts) {
            val storeSeasonEntity = StoreSeasonEntity()
            val storeEntity = storeMap[product.store]
            val seasonEntity = seasonMap[product.season]

            if(seasonEntity == null) {
                val seasonToInsert = SeasonEntity()
                seasonToInsert.id = product.season.toString()
                seasonRepository.save(seasonToInsert)
            }
            val compositePK = StoreSeasonPK()
            compositePK.season = product.season
            compositePK.store = product.store

            storeSeasonEntity.storeSeasonKey = compositePK
            storeSeasonEntity.store = storeEntity
            storeSeasonEntity.season = seasonEntity
            // get product with name product.product and season = product.season and season.store = product.store
            // productRepository.findAllById(product.product)
            storeSeasonEntityList.add(storeSeasonEntity)
        }
        storeSeasonRepository.saveAll(storeSeasonEntityList)
    }
}