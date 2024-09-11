package com.jys.catsapp.data.database.entity
import android.graphics.Bitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    var position: Int? = 0
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


