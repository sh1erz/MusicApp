package com.example.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Provides
    fun provideService(): DeezerService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        if (BuildConfig.DEBUG) {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BASIC)
                })
                .build()
            retrofitBuilder.client(client)
        }

        return retrofitBuilder.build().create(DeezerService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.deezer.com/"
    }
}