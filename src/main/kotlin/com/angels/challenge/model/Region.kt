package com.angels.challenge.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Region (
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("type")
    val type: String?,
    @JsonProperty("clusters")
    val clusters: String?
)