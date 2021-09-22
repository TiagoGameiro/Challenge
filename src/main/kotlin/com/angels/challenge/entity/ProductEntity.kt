package com.angels.challenge.entity

import reactor.util.annotation.NonNull
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name="product")
class ProductEntity() : Serializable {
    @Id
    @Column(name = "sku")
    @NonNull
    lateinit var sku: String

    @ManyToOne
    @JoinColumns(
        JoinColumn(name = "store_pk"),
        JoinColumn(name = "season_id")
    )
    var store: StoreSeasonEntity? = null

    @Column(name = "model")
    @NonNull
    lateinit var model: String

    @Column(name = "size")
    @NonNull
    lateinit var size: String

    @Column(name = "ean")
    @NonNull
    lateinit var ean: String

    @Column(name = "description")
    lateinit var description: String
}