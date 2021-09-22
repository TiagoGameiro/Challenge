package com.angels.challenge.repository

import com.angels.challenge.entity.ClusterEntity
import org.springframework.data.repository.CrudRepository

interface ClusterRepository : CrudRepository<ClusterEntity, Long> {
    fun findByName(name: String): ClusterEntity

    override fun findAll(): List<ClusterEntity>
}