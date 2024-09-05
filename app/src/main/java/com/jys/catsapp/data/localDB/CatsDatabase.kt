package com.jys.catsapp.data.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [PhotoEntity::class, PhotoRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class CatsDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun photoRemoteKeyDao(): PhotoRemoteKeyDao
}