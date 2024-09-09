package com.jys.catsapp.data.database.mapper

import com.jys.catsapp.data.database.entity.PhotoEntity
import com.jys.catsapp.data.database.entity.SrcEntity
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.data.network.model.Src

fun Photo.toEntity(page: Int): PhotoEntity {
    return PhotoEntity(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor ?: "",
        src = this.src.toEntity(),
        alt = this.alt,
        liked = this.liked,
        pageNumber = page
    )
}

fun Src.toEntity(): SrcEntity {
    return SrcEntity(
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