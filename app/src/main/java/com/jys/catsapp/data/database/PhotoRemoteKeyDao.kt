package com.jys.catsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jys.catsapp.data.database.entity.PhotoRemoteKeyEntity

@Dao
interface PhotoRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: PhotoRemoteKeyEntity)

    @Query("SELECT * FROM photo_remote_keys WHERE photoId = :photoId")
    suspend fun remoteKeyByPhoto(photoId: String): PhotoRemoteKeyEntity?

    @Query("DELETE FROM photo_remote_keys")
    suspend fun clearRemoteKeys()
}