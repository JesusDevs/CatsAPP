package com.jys.catsapp.data.localDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_keys")
data class PhotoRemoteKey(
    @PrimaryKey val photoId: String,
    val prevPageKey: Int?,
    val nextPageKey: Int?
)