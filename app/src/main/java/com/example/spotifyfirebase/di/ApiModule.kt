package com.example.spotifyfirebase.di

import android.content.Context
import android.content.SharedPreferences
import com.example.spotifyfirebase.api.SpotifyApi
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.BASE_URL
import com.example.spotifyfirebase.utils.Constants.TOKEN_SHARED
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providerApi(
        spotifyApi: SpotifyApi
    ) = ApiRepository(spotifyApi)

    @Provides
    @Singleton
    fun providerRetrofit(
        okHttpClient: OkHttpClient
    ):SpotifyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpotifyApi::class.java)

    @Provides
    @Singleton
    fun providerOkHttp(
        sharedPreferences: SharedPreferences
    ):OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val token = sharedPreferences.getString(TOKEN_SHARED, "")
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    fun providerTokenPref(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(TOKEN_SHARED, Context.MODE_PRIVATE)
}