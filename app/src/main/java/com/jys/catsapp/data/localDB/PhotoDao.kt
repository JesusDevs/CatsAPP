package com.jys.catsapp.data.localDB

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun clearAll()
}


@Dao
interface PhotoRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: PhotoRemoteKey)

    @Query("SELECT * FROM photo_remote_keys WHERE photoId = :photoId")
    suspend fun remoteKeyByPhoto(photoId: String): PhotoRemoteKey?

    @Query("DELETE FROM photo_remote_keys")
    suspend fun clearRemoteKeys()
}


@Entity(tableName = "photo_remote_keys")
data class PhotoRemoteKey(
    @PrimaryKey val photoId: String,
    val prevPageKey: Int?,  // Clave para la página anterior
    val nextPageKey: Int?   // Clave para la próxima página
)
