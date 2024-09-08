package com.jys.catsapp.data.localDB
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.data.network.model.Src

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Int?,
    val alt: String?,
    val photographer: String?,
    val url: String?,
    @Embedded(prefix = "src_") val src: SrcEntity?,
    val avgColor: String,
    val height: Int?,
    val liked: Boolean?,
    val photographerId: Int?,
    val photographerUrl: String?,
    val width: Int?,
    val pageNumber: Int? = 0,
    var position: Int? = 0 // Campo agregado para el orden
)


data class SrcEntity(
    val original: String?,
    val large2x: String?,
    val large: String?,
    val medium: String?,
    val small: String?,
    val portrait: String?,
    val landscape: String?,
    val tiny: String?
)

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
