package com.example.examentp_api

import com.example.examentp_api.ui.theme.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumAPI {
    @GET("/photos/{id}")
    suspend fun getphotosById(
        @Path("id") searchById: Int.Companion
    ): Response<List<Album>>
    @GET("/1/photos/")
    suspend fun getPhotos() : Response<List<Album>>

    @GET("/")
    suspend fun getPhoto() : Response<List<Album>>
}