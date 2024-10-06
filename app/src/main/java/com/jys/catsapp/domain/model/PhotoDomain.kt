package com.jys.catsapp.domain.model


import androidx.compose.runtime.Immutable
import com.jys.catsapp.data.database.entity.PhotoEntity

@Immutable
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
    val liked: Boolean?,
    val pageNumber: Int?
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
        liked = this.liked,
        pageNumber = this.pageNumber
    )
}


