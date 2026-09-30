package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.onboarding.AuthLandingScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyDescriptionScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyNameScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.ConfirmInformationScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CreateAccountScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.ForgotPasswordScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.LoginScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.UploadLogoScreen
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoutes : AppRoute {
    @Serializable
    data object AuthLandingRoute : AuthRoutes

    @Serializable
    data object CompanyDescriptionRoute : AuthRoutes

    @Serializable
    data object CompanyNameRoute : AuthRoutes

    @Serializable
    data object CreateAccountRoute : AuthRoutes

    @Serializable
    data object ForgotPassword : AuthRoutes

    @Serializable
    data object LoginScreenRoute : AuthRoutes

    @Serializable
    data object UploadLogoRoute : AuthRoutes

    @Serializable
    data object ConfirmInformation : AuthRoutes
}

fun EntryProviderScope<AppRoute>.authGraph(
    backStack: MutableList<AppRoute>,
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel,
) {
    entry<AuthRoutes.AuthLandingRoute> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            AuthLandingScreen(
                onCreateAccount = { backStack.add(AuthRoutes.CreateAccountRoute) },
                onLogin = { backStack.add(AuthRoutes.LoginScreenRoute) },
            )
        }
    }
    entry<AuthRoutes.CompanyDescriptionRoute> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            CompanyDescriptionScreen(
                authViewModel = authViewModel,
                onNext =
                    { backStack.add(AuthRoutes.UploadLogoRoute) },
            )
        }
    }
    entry<AuthRoutes.CompanyNameRoute> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            CompanyNameScreen(
                authViewModel = authViewModel,
                onNext = {
                    backStack.add(AuthRoutes.CompanyDescriptionRoute)
                },
            )
        }
    }
    entry<AuthRoutes.CreateAccountRoute> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            CreateAccountScreen(
                authViewModel = authViewModel,
                onNext = { backStack.add(AuthRoutes.CompanyNameRoute) },
                onSignIn = { backStack.add(AuthRoutes.LoginScreenRoute) },
            )
        }
    }
    entry<AuthRoutes.LoginScreenRoute> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            LoginScreen(
                authViewModel = authViewModel,
                onSignUp = { backStack.add(AuthRoutes.CreateAccountRoute) },
                onForgot = { backStack.add(AuthRoutes.ForgotPassword) },
            )
        }
    }
    entry<AuthRoutes.UploadLogoRoute> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            UploadLogoScreen(authViewModel = authViewModel) { backStack.add(AuthRoutes.ConfirmInformation) }
        }
    }

    entry<AuthRoutes.ConfirmInformation> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            ConfirmInformationScreen(authViewModel = authViewModel)
        }
    }

    entry<AuthRoutes.ForgotPassword> {
        AuthShell(mainViewModel = mainViewModel, authViewModel = authViewModel) {
            ForgotPasswordScreen(onClick = {
                backStack.add(AuthRoutes.LoginScreenRoute)
            })
        }
    }
}
