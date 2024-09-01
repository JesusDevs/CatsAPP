package com.jys.catsapp.domain.model

import com.jys.catsapp.data.localDB.PhotoEntity
import com.jys.catsapp.data.localDB.SrcEntity

data class PhotoDomain(
    val id: Int?,
    val width: Int?,
    val height: Int?,
    val url: String?,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    val src: SrcDomain,
    val alt: String?,
    val liked: Boolean?
)

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


fun PhotoEntity.toDomain(): PhotoDomain {
    return PhotoDomain(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = this.src?.toDomain() ?: SrcDomain(
            original = null,
            large2x = null,
            large = null,
            medium = null,
            small = null,
            portrait = null,
            landscape = null,
            tiny = null
        ),
        alt = this.alt,
        liked = this.liked
    )
}

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

