package com.sixteenhalves.orangeadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.sixteenhalves.orangeadmin.ntwk.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val networkService: NetworkService,
    ) : ViewModel() {
        private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

        private val _userState = MutableStateFlow(AuthFlowData())
        val userState = _userState.asStateFlow()

        fun updateEmail(email: String) {
            _userState.update { it.copy(email = email) }
        }

        fun updatePassword(password: String) {
            _userState.update { it.copy(password = password) }
        }

        fun updateCompanyDesc(desc: String) {
            _userState.update { it.copy(companyDescription = desc) }
        }

        fun updateCompanyName(name: String) {
            _userState.update { it.copy(companyName = name) }
        }

        fun submitToServer() {
            val currentState = _userState.value
            val email =
                currentState
                    .email
                    .toRequestBody("text/plain".toMediaTypeOrNull())
            val password =
                currentState
                    .password
                    .toRequestBody("text/plain".toMediaTypeOrNull())
            val companyName =
                currentState
                    .companyName
                    .toRequestBody("text/plain".toMediaTypeOrNull())
            val companyDesc =
                currentState
                    .companyDescription
                    .toRequestBody("text/plain".toMediaTypeOrNull())
            // remember to clear password & clear image uri in cache
//            networkService.createUser(email = email, password = password, companyName = companyName, companyDesc = companyDesc)
        }

data class AuthFlowData(
    val email: String = "",
    val password: String = "",
    val companyName: String = "",
    val companyDescription: String = "",
    val image: String? = null,
)
