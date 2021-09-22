package com.angels.challenge.repository

import com.angels.challenge.entity.StoreEntity
import com.angels.challenge.entity.StoreSeasonEntity
import org.springframework.data.repository.CrudRepository

interface StoreSeasonRepository : CrudRepository<StoreSeasonEntity, Long> {
    override fun findAll(): List<StoreSeasonEntity>
}