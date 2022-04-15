package com.criminal_code.rickandmorty.model.data.detail


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)