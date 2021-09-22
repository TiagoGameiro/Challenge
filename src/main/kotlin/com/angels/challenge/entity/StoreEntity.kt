package com.angels.challenge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name="store")
class StoreEntity() : Serializable {
    @Id
    @Column(name = "name")
    var name: String = ""

    @Column(name = "region")
    var region: String = ""

    @Column(name = "theme")
    var theme: String? = "null"

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name="regions_name")
    var relatedRegion: RegionEntity? = null

    @OneToMany(mappedBy = "store", fetch=FetchType.EAGER)
    var storeSeasons: MutableList<StoreSeasonEntity>? = null
}