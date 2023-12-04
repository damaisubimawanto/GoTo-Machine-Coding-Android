package com.gopay.dependencies

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gopay.BuildConfig
import com.gopay.data.services.HomeService
import com.gopay.utils.Constants.AUTH_TOKEN
import com.gopay.utils.Constants.HEADER_NAME_ACCEPT
import com.gopay.utils.Constants.HEADER_NAME_AUTHORIZATION
import com.gopay.utils.Constants.HEADER_NAME_GITHUB_VERSION
import com.gopay.utils.Constants.HEADER_VALUE_ACCEPT
import com.gopay.utils.Constants.HEADER_VALUE_GITHUB_VERSION
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory<GsonConverterFactory> {
        GsonConverterFactory.create()
    }
    factory <Gson> {
        GsonBuilder().create()
    }
    factory<RxJava2CallAdapterFactory> {
        RxJava2CallAdapterFactory.create()
    }
    factory {
        providesCache(get())
    }
    factory {
        providesOkhttp(get())
    }
    single {
        providesRetrofit(get(), get(), get())
    }
    single {
        get<Retrofit>().create(HomeService::class.java)
    }
}

private fun providesCache(context: Context) : Cache {
    val cacheSize = 10 * 1024 * 1024 // 10 MB
    return Cache(context.cacheDir, cacheSize.toLong())
}

private fun providesOkhttp(cache: Cache) : OkHttpClient {
    val client = OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .addHeader(HEADER_NAME_ACCEPT, HEADER_VALUE_ACCEPT)
                .addHeader(HEADER_NAME_AUTHORIZATION, "Bearer $AUTH_TOKEN")
                .addHeader(HEADER_NAME_GITHUB_VERSION, HEADER_VALUE_GITHUB_VERSION)
                .build()
            chain.proceed(newRequest)
        }

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)
    }

    return client.build()
}

private fun providesRetrofit(
    gsonConverterFactory: GsonConverterFactory,
    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
    okHttpClient: OkHttpClient
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .client(okHttpClient)
        .build()
}
