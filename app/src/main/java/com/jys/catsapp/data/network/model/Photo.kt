package com.jys.catsapp.data.network.model


import com.google.gson.annotations.SerializedName
import com.jys.catsapp.data.localDB.PhotoEntity
import com.jys.catsapp.data.localDB.toEntity


data class Photo(
    @SerializedName("alt")
    val alt: String?,
    @SerializedName("avg_color")
    val avgColor: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("liked")
    val liked: Boolean?,
    @SerializedName("photographer")
    val photographer: String?,
    @SerializedName("photographer_id")
    val photographerId: Int?,
    @SerializedName("photographer_url")
    val photographerUrl: String?,
    @SerializedName("src")
    val src: Src,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)
fun Photo.toEntity(page: Int, position: Int): PhotoEntity {
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
        pageNumber = page,
        position = position // Usamos la posición para ordenar
    )
}
