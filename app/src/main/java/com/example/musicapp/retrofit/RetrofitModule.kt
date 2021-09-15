package com.example.musicapp.retrofit

import android.util.Log
import com.example.musicapp.ui.main.LOG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideService(): DeezerService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                Log.i(LOG, "url: ${request.url()}")
                chain.proceed(request)
            }.build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(DeezerService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.deezer.com/"
    }
}