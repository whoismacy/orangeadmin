package com.sixteenhalves.orangeadmin.ntwk

import com.sixteenhalves.orangeadmin.domain.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkService {
    @GET("/user")
    suspend fun getUser(): Response<User?>

    @POST("/create-account")
    suspend fun createUser(): Response<User?>

    @POST("/login")
    suspend fun loginUser(): Response<User?>

    @POST("/logout")
    suspend fun logoutUser(): Response<User?>
}
