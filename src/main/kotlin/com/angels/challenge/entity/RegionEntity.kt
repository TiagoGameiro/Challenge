package com.angels.challenge.entity

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "region")
class RegionEntity() : Serializable {
    @Id
    @Column(name = "name")
    var name: String? = null

    @Column(name = "type")
    var type: String? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name="cluster_name")
    var cluster: ClusterEntity? = null

    @OneToMany(mappedBy = "relatedRegion")
    private var stores: List<StoreEntity>? = null

    @Column(name = "clusters")
    var clusters: String? = null
}