package com.jys.catsapp.domain.model

import com.jys.catsapp.data.database.entity.SrcEntity

data class SrcDomain(
    val original: String?,
    val large2x: String?,
    val large: String?,
    val medium: String?,
    val small: String?,
    val portrait: String?,
    val landscape: String?,
    val tiny: String?
)

fun SrcEntity.toDomain(): SrcDomain {
    return SrcDomain(
        original = this.original,
        large2x = this.large2x,
        large = this.large,
        medium = this.medium,
        small = this.small,
        portrait = this.portrait,
        landscape = this.landscape,
        tiny = this.tiny
    )
}