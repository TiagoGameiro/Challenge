package com.angels.challenge.repository

import com.angels.challenge.entity.ClusterEntity
import com.angels.challenge.entity.ProductEntity
import com.angels.challenge.entity.SeasonEntity
import com.angels.challenge.entity.StoreEntity
import org.springframework.data.repository.CrudRepository
import java.util.stream.Collectors

interface SeasonRepository : CrudRepository<SeasonEntity, Long> {
    fun findById(id: String): SeasonEntity?
    override fun findAll(): List<SeasonEntity>
}