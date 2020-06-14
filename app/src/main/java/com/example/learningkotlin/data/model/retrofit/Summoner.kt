package com.example.learningkotlin.data.model.retrofit

import com.google.gson.annotations.SerializedName

data class Summoner(
    @SerializedName("id")
    val id: String,
    @SerializedName("accountId")
    val accountId: String,
    @SerializedName("puuid")
    val puuid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileIconId")
    val profileIconID: Int,
    @SerializedName("revisionDate")
    val revisionDate : Long,
    @SerializedName("summonerLevel")
    val summonerLevel: Int
)

