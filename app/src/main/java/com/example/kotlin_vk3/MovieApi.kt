package com.example.kotlin_vk3

import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET("MoakIaJ_flFMq6EBTt9S_AvCrgOHYgETeMvwEDr2EsCSGHYs9su-cG3qZDM50koI5YQh4x-PXyR5KpFkK3V7NwQUmweXChd7FDYpqJ14LCfQ9TbFg5dBCg/popular_mockup.json")
    suspend fun getMovies(): Response<MovieListResponse>

    @GET("3jRGu6TYCiCCZ3GadOE8Wl0amqO_J0ujIYCZEYcb93xtwhm-BzJ72VVnqIa6N3blf0DRH2CCsYs_5CO0acaoAwodIHjZQlLfM0oHyJ1qRl7pC4UOdIudng/favourites_mockup.json")
    suspend fun getBookmarkedMovies(): Response<MovieListResponse>
}