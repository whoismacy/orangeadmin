package com.sixteenhalves.orangeadmin.domain

sealed interface AuthApplicationState {
    data object Loading : AuthApplicationState

    data object Idle : AuthApplicationState

    data class Error(
        val message: String,
    ) : AuthApplicationState
}
