package com.angels.challenge.repository

import com.angels.challenge.entity.ProductEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<ProductEntity, Long> {
    override fun findAll(): List<ProductEntity>

    @Query("SELECT p FROM ProductEntity p WHERE p.model = 'PD0_DEODORANT'")
    fun getProductByNameAndSeasonAndStore(): List<ProductEntity>
}