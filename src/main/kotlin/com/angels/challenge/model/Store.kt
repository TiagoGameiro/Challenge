package com.angels.challenge.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Store (
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("theme")
    val theme: String?,
    @JsonProperty("region")
    val region: String?
)