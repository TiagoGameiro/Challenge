package com.angels.challenge.model

import com.fasterxml.jackson.annotation.JsonProperty

data class StoreProduct (
    @JsonProperty("product")
    val product: String?,
    @JsonProperty("store")
    val store: String?,
    @JsonProperty("season")
    val season: String?
)