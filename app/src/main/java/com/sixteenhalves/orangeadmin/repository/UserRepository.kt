package com.sixteenhalves.orangeadmin.repository

import com.sixteenhalves.orangeadmin.domain.User

interface UserRepository {
    suspend fun getUser(): Result<User?>

    suspend fun createUser(): Result<User?>

    suspend fun loginUser(): Result<User?>

    suspend fun logoutUser(): Result<User?>
}
