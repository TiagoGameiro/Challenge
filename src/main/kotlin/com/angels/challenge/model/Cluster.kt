package com.angels.challenge.model

import com.angels.challenge.entity.ClusterEntity
import com.fasterxml.jackson.annotation.JsonProperty

data class Cluster (
    @JsonProperty("name")
    val name: String?
)