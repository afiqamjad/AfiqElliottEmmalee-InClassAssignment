package com.example.afiqelliottemmalee_inclassassignment

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "e2bc1bd"
interface MovieAPI {
    @GET("/?apikey=$API_KEY")
    suspend fun searchMovies(@Query("t") title: String): Movie
}