package com.gopay.dependencies

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gopay.persistance.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun providesRoomDB(context: Context): RoomDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
    }
}