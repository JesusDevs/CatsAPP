package com.jys.catsapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_keys")
data class PhotoRemoteKeyEntity(
    @PrimaryKey val photoId: String,
    val prevPageKey: Int?,
    val nextPageKey: Int?
)