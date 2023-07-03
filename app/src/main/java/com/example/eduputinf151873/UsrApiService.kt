package com.example.eduputinf151873

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsrApiService {

    @GET("/xmlapi2/collection")
    suspend fun getUserData(@Query("username") username: String): UserResponse

    @GET("/xmlapi2/collection")
    suspend fun getGamesData(@Query("username") username: String): GameListResponse

    @GET("/xmlapi2/collection")
    suspend fun getExtensionsData(@Query("username") username: String, @Query( "subtype") subtype:String ): ExtensionListResponse


}