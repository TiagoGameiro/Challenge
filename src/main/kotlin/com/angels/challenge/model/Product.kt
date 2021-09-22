package com.angels.challenge.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Product (
    @JsonProperty("season")
    val season: String?,
    @JsonProperty("model")
    val model: String?,
    @JsonProperty("size")
    val size: String?,
    @JsonProperty("SKU")
    val sku: String?,
    @JsonProperty("ean")
    val ean: String?,
    @JsonProperty("description")
    val description: String?
)