package com.sixteenhalves.orangeadmin.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.domain.MainApplicationState
import com.sixteenhalves.orangeadmin.domain.User
import com.sixteenhalves.orangeadmin.repository.BiometricRepository
import com.sixteenhalves.orangeadmin.repository.UserRepository
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val biometricRepository: BiometricRepository,
    ) : ViewModel() {
        private val _user = MutableLiveData<User?>()
        val user = _user as LiveData<User?>

        private val _branchesExist = MutableStateFlow<Boolean>(false)
        val branchesExist = _branchesExist.asStateFlow()

        private val _mainApplicationState = MutableStateFlow(MainApplicationState.Idle)
        val mainApplicationState = _mainApplicationState.asStateFlow()

        val snackbarHostState = SnackbarHostState()

        init {
            viewModelScope.launch {
                try {
                    val result = userRepository.getUser()
                    _user.value = result.getOrNull()
                } catch (e: Exception) {
                    Log.e("MAIN VIEW MODEL", "Error: ${e.message}")
                }
            }
        }

        fun triggerEvent(message: String) {
            EventManager.triggerEvent(EventManager.AppEvent.ShowEvent(message))
        }

        fun isBiometricsAvailable(context: Context): Boolean = biometricRepository.isBiometricAvailable(context)

        fun showBiometricPrompt(activity: FragmentActivity) {
            biometricRepository.showBiometricPrompt(activity, onFailure = {}, onSuccess = {})
        }

        fun readCsvFile() {}
    }
