package com.angels.challenge.repository

import com.angels.challenge.entity.StoreEntity
import org.springframework.data.repository.CrudRepository
import java.util.stream.Collectors

interface StoreRepository : CrudRepository<StoreEntity, Long> {
    fun findByName(name: String): StoreEntity
    override fun findAll(): List<StoreEntity>
}