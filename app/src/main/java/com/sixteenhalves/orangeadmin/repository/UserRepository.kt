package com.sixteenhalves.orangeadmin.repository

import com.sixteenhalves.orangeadmin.domain.User
import com.sixteenhalves.orangeadmin.viewmodels.AuthLoginFlowData
import okhttp3.MultipartBody

interface UserRepository {
    suspend fun getUser(): Result<User?>

    suspend fun createUser(
        email: String,
        name: String,
        password: String,
        companyName: String,
        companyDescription: String,
        logo: MultipartBody.Part?,
    ): Result<User?>

    suspend fun loginUser(request: AuthLoginFlowData): Result<User?>

    suspend fun logoutUser(): Result<User?>
}
