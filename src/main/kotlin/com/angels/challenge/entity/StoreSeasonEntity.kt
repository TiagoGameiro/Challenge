package com.angels.challenge.entity

import com.angels.challenge.model.StoreSeasonPK
import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "store_season")
class StoreSeasonEntity() : Serializable {
    @EmbeddedId
    var storeSeasonKey: StoreSeasonPK? = null

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "store_name", referencedColumnName = "name")
    var store: StoreEntity? = null

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    var season: SeasonEntity? = null

    @JsonIgnore
    @OneToMany(mappedBy = "store")
    var storeProductsPerSeason: MutableList<ProductEntity>? = null
}