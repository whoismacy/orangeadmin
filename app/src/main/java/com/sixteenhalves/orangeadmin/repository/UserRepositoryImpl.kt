package com.sixteenhalves.orangeadmin.repository

import com.sixteenhalves.orangeadmin.domain.User
import com.sixteenhalves.orangeadmin.ntwk.NetworkService
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val networkService: NetworkService,
    ) : UserRepository {
        override suspend fun createUser(): Result<User?> =
            try {
                val response = networkService.createUser()
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(Exception("Network error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getUser(): Result<User?> =
            try {
                val response = networkService.getUser()
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(Exception("Network error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun loginUser(): Result<User?> =
            try {
                val response = networkService.loginUser()
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(Exception("Network error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun logoutUser(): Result<User?> =
            try {
                val response = networkService.logoutUser()
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(Exception("Network error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
