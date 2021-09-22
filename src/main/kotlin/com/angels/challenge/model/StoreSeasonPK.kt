package com.angels.challenge.model

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class StoreSeasonPK : Serializable {
    @Column(name = "store")
    var store: String? = null

    @Column(name = "season")
    var season: String? = null

    override fun hashCode(): Int {
        return Objects.hash(store, season)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val other: StoreSeasonPK = other as StoreSeasonPK
        return Objects.equals(store, other.store) && Objects.equals(season, other.season)
    }
}