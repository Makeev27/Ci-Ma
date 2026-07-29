package com.makeev.cima.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.makeev.cima.BuildConfig
import com.makeev.cima.data.remote.api.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {


    companion object {

        @Singleton
        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor { message ->
                Log.d("NetworkLog", "$message ")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Singleton
        @Provides
        fun provideOkhttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
        ): OkHttpClient {

            val authInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()

                val urlWithLanguage = originalRequest.url.newBuilder()
                    .addQueryParameter("language", "ru-RU")
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(urlWithLanguage)
                    .header("Authorization", "Bearer ${BuildConfig.TMDB_BEARER_TOKEN}")
                    .header("accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }

            return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        val networkJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }


        @Singleton
        @Provides
        fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(okHttpClient)
                .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
                .build()
        }

        @Singleton
        @Provides
        fun provideTMDBApi(retrofit: Retrofit): TMDBApi {
            return retrofit.create(TMDBApi::class.java)
        }


    }

}