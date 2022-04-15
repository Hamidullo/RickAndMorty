package com.criminal_code.rickandmorty.model.api

import com.criminal_code.rickandmorty.model.data.character.Character
import com.criminal_code.rickandmorty.model.data.detail.Detail
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://rickandmortyapi.com/api/"

interface Api {
    @GET("character/")
    suspend fun loadCharacter(
        @Query("page") page: Int
    ): Character

    @GET("character/{id}")
    suspend fun loadDetail(
        @Path("id") id: Int
    ): Detail

    companion object {
        operator fun invoke(): Api {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(Api::class.java)
        }
    }
}