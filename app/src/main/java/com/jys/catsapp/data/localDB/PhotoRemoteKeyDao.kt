package com.jys.catsapp.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: PhotoRemoteKey)

    @Query("SELECT * FROM photo_remote_keys WHERE photoId = :photoId")
    suspend fun remoteKeyByPhoto(photoId: String): PhotoRemoteKey?

    @Query("DELETE FROM photo_remote_keys")
    suspend fun clearRemoteKeys()
}