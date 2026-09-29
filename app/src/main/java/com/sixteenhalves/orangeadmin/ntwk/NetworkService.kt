package com.sixteenhalves.orangeadmin.ntwk

import com.sixteenhalves.orangeadmin.domain.User
import com.sixteenhalves.orangeadmin.viewmodels.AuthLoginFlowData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetworkService {
    @GET("/api/user")
    suspend fun getUser(): Response<User?>

    @Multipart
    @POST("/api/register")
    suspend fun createUser(
        @Part("email") email: String,
        @Part("name") name: String,
        @Part("password") password: String,
        @Part("company_name") companyName: String,
        @Part("company_desc") companyDescription: String,
        @Part logo: MultipartBody.Part?,
    ): Response<User?>

    @POST("/api/login")
    suspend fun loginUser(
        @Body request: AuthLoginFlowData,
    ): Response<User?>

    @POST("/api/logout")
    suspend fun logoutUser(): Response<User?>
}
