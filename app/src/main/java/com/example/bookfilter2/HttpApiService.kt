package com.example.bookfilter2

import retrofit2.http.GET

interface HttpApiService {
    @GET("/books")
    suspend fun getMyBookData():List<Bookdata>
}