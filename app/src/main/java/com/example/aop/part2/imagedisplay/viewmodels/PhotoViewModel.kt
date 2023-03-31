package com.example.aop.part2.imagedisplay.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aop.part2.imagedisplay.models.PhotoResponseItem
import com.example.aop.part2.imagedisplay.retrofit.repository.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class PhotoViewModel : ViewModel() {

    private val _photoItemList = MutableLiveData<MutableList<PhotoResponseItem>>()
    val photoItemList: LiveData<MutableList<PhotoResponseItem>> = _photoItemList

    private val _searchTerm = MutableLiveData<String?>(null)
    val searchTerm: LiveData<String?> = _searchTerm

    fun getRandomPhotos(count: Int) {
        val service = RetrofitApi.unsplashApiService
        service.getPhotos(count)
            .enqueue(object : Callback<List<PhotoResponseItem>> {
                override fun onResponse(
                    call: Call<List<PhotoResponseItem>>,
                    response: Response<List<PhotoResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("JB", response.body().toString())
                        _photoItemList.value = response.body() as MutableList<PhotoResponseItem>
                    }
                }
                override fun onFailure(call: Call<List<PhotoResponseItem>>, t: Throwable) {
                    Log.d("JB", t.message.toString())
                }
            })
    }

    fun getSearchPhotos(query: String? = null) {
        val service = RetrofitApi.unsplashApiService
        service.searchPhotos(query)
            .enqueue(object : Callback<List<PhotoResponseItem>> {
                override fun onResponse(
                    call: Call<List<PhotoResponseItem>>,
                    response: Response<List<PhotoResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("JB", response.body().toString())
                        _photoItemList.value = response.body() as MutableList<PhotoResponseItem>
                        _searchTerm.value = query
                    }
                }
                override fun onFailure(call: Call<List<PhotoResponseItem>>, t: Throwable) {
                    Log.d("JB", t.message.toString())
                }
            })
    }
}