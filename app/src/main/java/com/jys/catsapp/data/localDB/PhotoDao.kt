package com.jys.catsapp.data.localDB

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {

    // Método para obtener fotos paginadas con límites y offsets específicos (sin PagingSource)
    @Query("SELECT * FROM photos LIMIT :limit OFFSET :offset")
    suspend fun getAllPhotosPaginated(limit: Int, offset: Int): List<PhotoEntity>

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun clearAll()
}

