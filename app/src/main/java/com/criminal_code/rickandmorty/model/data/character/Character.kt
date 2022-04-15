package com.criminal_code.rickandmorty.model.data.character


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("info")
    val info: Info = Info(),
    @SerializedName("results")
    val results: List<Result> = listOf()
)