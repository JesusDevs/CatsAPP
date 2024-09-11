package com.jys.catsapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jys.catsapp.data.database.entity.PhotoEntity

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos ORDER BY pageNumber ASC, position ASC")
    fun getAllPhotos(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun clearAll()
}







