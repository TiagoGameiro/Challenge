package com.angels.challenge.entity

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name="season")
class SeasonEntity() : Serializable {
    @Id
    @Column(name = "id")
    var id: String? = null

    @OneToMany(mappedBy = "season")
    var storeSeasons: List<StoreSeasonEntity>? = null
}