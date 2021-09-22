package com.angels.challenge.repository

import com.angels.challenge.entity.RegionEntity
import org.springframework.data.repository.CrudRepository

interface RegionRepository : CrudRepository<RegionEntity, Long> {
    fun findByName(name: String): RegionEntity

    override fun findAll(): List<RegionEntity>
}