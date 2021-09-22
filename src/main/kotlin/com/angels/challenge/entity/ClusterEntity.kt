package com.angels.challenge.entity

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name="cluster")
class ClusterEntity() : Serializable {
    @Id
    @Column(name = "name")
    var name: String? = null

    @OneToMany(mappedBy = "cluster")
    private var regions: List<RegionEntity>? = null
}