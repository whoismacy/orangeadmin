package com.sixteenhalves.orangeadmin.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor() : ViewModel() {
        private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    }
