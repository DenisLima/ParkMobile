package com.djv.data.di

import com.djv.data.MusicRepositoryImpl
import com.djv.data.remotesource.MusicRemoteSource
import com.djv.domain.MusicRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://itunes.apple.com/"

val dataModule = module {

    factory {
        Gson()
    }

    single {
        GsonConverterFactory.create(get<Gson>())
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(get<GsonConverterFactory>())
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        get<Retrofit>().create(MusicRemoteSource::class.java)
    }

    single<MusicRepository> {
        MusicRepositoryImpl(get())
    }
}