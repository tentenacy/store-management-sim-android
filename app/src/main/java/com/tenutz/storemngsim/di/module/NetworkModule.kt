package com.tenutz.storemngsim.di.module

import com.google.gson.GsonBuilder
import com.tenutz.storemngsim.BuildConfig
import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
    ): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val gsonConverter1 = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val gsonConverter2 = GsonBuilder()
            .setDateFormat("yyyyMMddHHmmss")
            .create()

        val gsonConverter3 = GsonBuilder()
            .setDateFormat("yyyyMMdd")
            .create()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonConverter1))
            .addConverterFactory(GsonConverterFactory.create(gsonConverter2))
            .addConverterFactory(GsonConverterFactory.create(gsonConverter3))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
    }

    @Provides
    fun provideOMSApi(retrofitBuilder: Retrofit.Builder): SCKApi {
        return retrofitBuilder
            .baseUrl(BuildConfig.API_URL_BASE)
            .build()
            .create()
    }
}