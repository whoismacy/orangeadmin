package com.sixteenhalves.orangeadmin.domain

sealed interface MainApplicationState {
    data object Loading : MainApplicationState

    data object Idle : MainApplicationState

    data class Error(
        val message: String,
    ) : MainApplicationState
}
