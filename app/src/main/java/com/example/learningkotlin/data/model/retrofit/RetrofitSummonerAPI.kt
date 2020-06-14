package com.example.learningkotlin.data.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitSummonerAPI {

    @GET("summoner/v4/summoners/by-name/{summoner_name}")
    public fun getSummonerByName(@Path("summoner_name") summonerName: String, @Query("api_key") apiKey : String) : Call<Summoner>
}