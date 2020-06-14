package com.example.learningkotlin.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningkotlin.data.model.retrofit.RetrofitSummonerAPI
import com.example.learningkotlin.data.model.retrofit.Summoner
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitRepository constructor(val retrofit: Retrofit) {

    private val API_KEY: String = "RGAPI-17ad389a-2875-49f5-8631-0b9a558b1bf6"
    private val TAG = "RetrofitRepository"

    private val retrofitSummonerAPI: RetrofitSummonerAPI = retrofit.create(com.example.learningkotlin.data.model.retrofit.RetrofitSummonerAPI::class.java)
    fun getSummoner(summonerString: String?): Observable<Summoner> {
        val summonerObservable  = BehaviorSubject.create<Summoner>()
        val call = retrofitSummonerAPI.getSummonerByName(summonerString!!,API_KEY).enqueue(object :
            Callback<Summoner> {
            override fun onResponse(call: Call<Summoner>, response: Response<Summoner>) {

                var data: Summoner  = (response.body() as Summoner)!!

                summonerObservable.onNext(data)

            }

            override fun onFailure(call: Call<Summoner>, t: Throwable) {
                Log.e(TAG,t.message)
            }
        })
        return summonerObservable
    }

}
