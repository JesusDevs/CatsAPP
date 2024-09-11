package com.jys.catsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jys.catsapp.data.database.entity.PhotoEntity
import com.jys.catsapp.data.database.entity.PhotoRemoteKeyEntity

@Database(
    entities = [PhotoEntity::class, PhotoRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatsDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun photoRemoteKeyDao(): PhotoRemoteKeyDao
}