package com.gopay.dependencies

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gopay.persistance.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "gopay_sharedpreferences",
            MODE_PRIVATE
        )
    }
    single<RoomDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_db").build()
    }
}
