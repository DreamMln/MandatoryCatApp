package com.example.mandatorycatapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mandatorycatapp.models.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatsRepository {
    private val url = "https://anbo-restlostcats.azurewebsites.net/api/"

    //servicen
    private val catsService: CatsService
    val catsLiveData: MutableLiveData<List<Cat>> = MutableLiveData<List<Cat>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    //init fungere som en con
    init {
        Log.d("APPLE", "init: jeg var her" )
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()) //GSON minder om JSON
            .build()
        catsService = build.create(CatsService::class.java)
        getCats()
    }
    fun getCats() {
        catsService.getAllCats().enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", response.body().toString())
                    val b: List<Cat>? = response.body()
                    catsLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }
            //eller hvis det slet ikke virker
            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("FUCK!", t.message!!)
            }
        })
    }
    //add/post
    fun add(cat: Cat){
        catsService.addCats(cat).enqueue(object : Callback<Cat>{
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful){
                    Log.d("APPPLLLEEEE", "Added: " + response.body())
                    getCats()
                }else{
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }
            //eller hvis det slet ikke virker
            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
    fun delete(id: Int){
        catsService.deleteCat(id).enqueue(object : Callback<Cat>{
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful){
                    Log.d("APPLE", "Added: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                    //kald på get, så den opdatere
                    getCats()
                }else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                    }
                }
                //eller hvis det slet ikke virker
                override fun onFailure(call: Call<Cat>, t: Throwable) {
                    errorMessageLiveData.postValue(t.message)
                    Log.d("APPLE", t.message!!)
                }
            })
        }
    fun sortByName(){
        catsLiveData.value = catsLiveData.value?.sortedBy { it.name }
    }
    fun sortByNameDesending() {
        catsLiveData.value = catsLiveData.value?.sortedByDescending { it.name }
    }
    fun sortByDes(){
        catsLiveData.value = catsLiveData.value?.sortedBy { it.description }
    }
    fun filterByName(name: String){
        if (name.isBlank()){
            getCats()
        }else{
            catsLiveData.value = catsLiveData.value?.filter { cat -> cat.name.contains(name) }
        }
    }
}
