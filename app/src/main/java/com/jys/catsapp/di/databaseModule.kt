package com.jys.catsapp.di

import androidx.room.Room
import com.jys.catsapp.data.database.CatsDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            get(),
            CatsDatabase::class.java,
            "cats_database"
        ).build()
    }

    single { get<CatsDatabase>().photoDao() }
}