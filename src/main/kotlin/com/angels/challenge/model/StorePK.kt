package com.angels.challenge.model

import com.angels.challenge.entity.RegionEntity
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class StorePK (
    var name: String = "",
    var region: String = ""
) : Serializable