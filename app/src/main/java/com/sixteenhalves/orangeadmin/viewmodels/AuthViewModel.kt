package com.sixteenhalves.orangeadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixteenhalves.orangeadmin.domain.AuthApplicationState
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.ntwk.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val networkService: NetworkService,
    ) : ViewModel() {
        private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(true)
        val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

        private val _authApplicationState = MutableStateFlow<AuthApplicationState>(AuthApplicationState.Idle)
        val authApplicationState = _authApplicationState.asStateFlow()

        private val _userState = MutableStateFlow(AuthRegisterFlowData())
        val userState = _userState.asStateFlow()

        private fun triggerEvent(message: String) {
            EventManager.triggerEvent(EventManager.AppEvent.ShowEvent(message))
        }

        fun updateEmail(email: String) {
            _userState.update { it.copy(email = email) }
        }

        fun updateName(name: String) {
            _userState.update { it.copy(name = name) }
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

        fun updateImage(file: File) {
            _userState.update { it.copy(image = file.absolutePath) }
        }

        fun registerToServer() {
            val currentState = userState.value
            var imagePart: MultipartBody.Part? = null
            currentState.image?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    val requestFile = file.asRequestBody("image/webp".toMediaTypeOrNull())
                    imagePart = MultipartBody.Part.createFormData("logo", file.name, requestFile)
                }
            }
            _authApplicationState.value = AuthApplicationState.Loading
            viewModelScope.launch {
                try {
                    val response =
                        networkService.createUser(
                            email = currentState.email,
                            name = currentState.name,
                            password = currentState.password,
                            companyName = currentState.companyName,
                            companyDescription = currentState.companyDescription,
                            logo = imagePart,
                        )

                    if (response.isSuccessful) {
                        triggerEvent("Account Successfully Created🎊")
                    } else {
                        triggerEvent("${response.message()}; An error occurred while creating your account")
                    }
                } catch (e: Exception) {
                    triggerEvent("Error while trying to create User: ${e.message}")
                } finally {
                    updatePassword("0")
                }
            }
        }

        fun loginToServer(
            email: String,
            password: String,
        ) {
            _authApplicationState.value = AuthApplicationState.Loading
            viewModelScope.launch {
                try {
                    val response =
                        networkService
                            .loginUser(AuthLoginFlowData(email = email, password = password))
                    if (response.isSuccessful) {
                        triggerEvent("Login Successful")
                        // route to main page
                    } else {
                        triggerEvent("${response.message()}; An error occurred while trying to log in")
                    }
                } catch (e: Exception) {
                    triggerEvent("Network error occurred: ${e.message}")
                }
            }
        }
    }

data class AuthRegisterFlowData(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val companyName: String = "",
    val companyDescription: String = "",
    val image: String? = null,
)

data class AuthLoginFlowData(
    val email: String = "",
    val password: String = "",
)
