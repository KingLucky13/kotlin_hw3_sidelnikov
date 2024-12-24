package com.example.kotlin_vk3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel:ViewModel() {
    private val _movies = MutableStateFlow<List<MovieShort>>(emptyList())
    val movies: StateFlow<List<MovieShort>> get() = _movies

    fun fetchMovies() {
        viewModelScope.launch {
            val retrofit = getRetrofit()

            val movieApi: MovieApi = retrofit.create(MovieApi::class.java)

            viewModelScope.launch {
                val apiMovies = movieApi.getMovies().body()!!.movies
                _movies.value = apiMovies
            }

        }
    }

    fun fetchBookmarkedMovies(){
        viewModelScope.launch {
            val retrofit = getRetrofit()

            val movieApi: MovieApi = retrofit.create(MovieApi::class.java)

            viewModelScope.launch {
                val apiMovies = movieApi.getBookmarkedMovies().body()!!.movies
                _movies.value = apiMovies
            }
        }
    }

    fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl("https://psv4.userapi.com/s/v1/d/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}