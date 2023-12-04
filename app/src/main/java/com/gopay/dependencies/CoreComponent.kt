package com.gopay.dependencies

import android.content.SharedPreferences
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.gopay.dispatcher.CoroutineDispatcherProvider
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        StorageModule::class,
        ImageModule::class,
        DispatcherModule::class]
)
interface CoreComponent {

    fun retrofit(): Retrofit

    fun gson(): Gson

    fun sharedPreferences(): SharedPreferences

    fun roomDb(): RoomDatabase

    fun picasso(): Picasso

    fun coroutineDispatcher(): CoroutineDispatcherProvider
}
