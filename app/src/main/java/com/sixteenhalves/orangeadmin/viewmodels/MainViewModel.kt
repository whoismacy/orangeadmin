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
import com.sixteenhalves.orangeadmin.domain.NotificationPreferences
import com.sixteenhalves.orangeadmin.domain.ThemePreference
import com.sixteenhalves.orangeadmin.domain.User
import com.sixteenhalves.orangeadmin.repository.BiometricRepository
import com.sixteenhalves.orangeadmin.repository.SettingsPreferencesRepository
import com.sixteenhalves.orangeadmin.repository.UserRepository
import com.sixteenhalves.orangeadmin.utils.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val biometricRepository: BiometricRepository,
        private val settingsPreferencesRepository: SettingsPreferencesRepository,
    ) : ViewModel() {
        private val _user = MutableLiveData<User?>()
        val user = _user as LiveData<User?>

        private val _branchesExist = MutableStateFlow(false)
        val branchesExist = _branchesExist.asStateFlow()

        private val _mainApplicationState = MutableStateFlow(MainApplicationState.Idle)
        val mainApplicationState = _mainApplicationState.asStateFlow()

        val themePreference: StateFlow<ThemePreference> =
            settingsPreferencesRepository.themePreference.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ThemePreference.SYSTEM_DEFAULT,
            )

        val notificationPreferences: StateFlow<NotificationPreferences> =
            settingsPreferencesRepository.notificationPreferences.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = NotificationPreferences(),
            )

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

        fun updateTheme(themePreference: ThemePreference) {
            viewModelScope.launch {
                settingsPreferencesRepository.updateThemePreference(themePreference)
            }
        }

        fun updateNotificationPreferences(
            lowStockAlerts: Boolean? = null,
            notificationSound: Boolean? = null,
            notificationVibration: Boolean? = null,
        ) {
            viewModelScope.launch {
                settingsPreferencesRepository.updateNotificationPreference(
                    lowStockAlerts = lowStockAlerts,
                    notificationSound = notificationSound,
                    notificationVibration = notificationVibration,
                )
            }
        }

        fun testNotification(context: Context) {
            val currentPrefs = notificationPreferences.value
            NotificationHelper.showLowStockNotification(
                context = context,
                itemName = "Navel Oranges",
                remainingStock = 2,
                preferences = currentPrefs
            )
        }

        fun readCsvFile() {}
    }
