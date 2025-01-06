package uk.ac.tees.mad.SV.Data.Remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/planetary/apod")
    suspend fun fetchApod(
        @Query("api_key") apiKey: String,
        @Query("date") date : String
    ): Response<Apod>
}