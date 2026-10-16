package com.sixteenhalves.orangeadmin.repository

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.sixteenhalves.orangeadmin.domain.EventManager
import javax.inject.Inject

class BiometricRepository
    @Inject
    constructor() {
        private val promptInfo =
            BiometricPrompt.PromptInfo
                .Builder()
                .apply {
                    setTitle("Biometric Protection for the application")
                    setSubtitle("Log in using your biometric credential")
                    setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }.build()

        private fun triggerEvent(message: String) {
            EventManager.triggerEvent(EventManager.AppEvent.ShowEvent(message))
        }

        fun isBiometricAvailable(context: Context): Boolean {
            val biometricManager = BiometricManager.from(context)
            return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    true
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    triggerEvent("No biometric hardware available")
                    false
                }

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    triggerEvent("No biometric hardware available")
                    false
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    triggerEvent("No biometrics enrolled")
                    false
                }

                else -> {
                    false
                }
            }
        }

        fun showBiometricPrompt(
            activity: FragmentActivity,
            onSuccess: () -> Unit,
            onFailure: () -> Unit,
        ) {
            val executor = ContextCompat.getMainExecutor(activity)
            val biometricPrompt =
                BiometricPrompt(
                    activity,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence,
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            triggerEvent("Biometric Authentication error: $errString")
                            onFailure()
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            triggerEvent("Biometric Authentication Failure")
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            onSuccess()
                        }
                    },
                )

            biometricPrompt.authenticate(promptInfo)
        }

        @RequiresApi(Build.VERSION_CODES.R)
        fun promptEnrollBiometric(context: Context) {
            try {
                val enrollIntent =
                    Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG)
                    }
                if (context is FragmentActivity) {
                    context.startActivity(enrollIntent)
                }
            } catch (e: ActivityNotFoundException) {
                if (context is FragmentActivity) {
                    context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                }
            }
        }
    }
