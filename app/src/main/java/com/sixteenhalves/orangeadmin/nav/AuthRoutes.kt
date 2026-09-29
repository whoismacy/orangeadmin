package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.onboarding.AuthLandingScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyDescriptionScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyNameScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CreateAccountScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.LoginScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.UploadLogoScreen
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
    data object LoginScreenRoute : AuthRoutes

    @Serializable
    data object UploadLogoRoute : AuthRoutes

    @Serializable
    data object ConfirmInformation : AuthRoutes
}

fun EntryProviderScope<AppRoute>.authGraph(
    backStack: MutableList<AppRoute>,
    mainViewModel: MainViewModel,
) {
    entry<AuthRoutes.AuthLandingRoute> {
        AuthShell(mainViewModel = mainViewModel) {
            AuthLandingScreen(
                onCreateAccount = { backStack.add(AuthRoutes.CreateAccountRoute) },
                onLogin = { backStack.add(AuthRoutes.LoginScreenRoute) },
            )
        }
    }
    entry<AuthRoutes.CompanyDescriptionRoute> {
        AuthShell(mainViewModel = mainViewModel) {
            CompanyDescriptionScreen(
                onNext =
                    { backStack.add(AuthRoutes.UploadLogoRoute) },
            )
        }
    }
    entry<AuthRoutes.CompanyNameRoute> {
        AuthShell(mainViewModel = mainViewModel) {
            CompanyNameScreen(onNext = {
                backStack.add(AuthRoutes.CompanyDescriptionRoute)
            })
        }
    }
    entry<AuthRoutes.CreateAccountRoute> {
        AuthShell(mainViewModel = mainViewModel) {
            CreateAccountScreen(
                onNext = { backStack.add(AuthRoutes.CompanyNameRoute) },
                onSignIn = { backStack.add(AuthRoutes.LoginScreenRoute) },
            )
        }
    }
    entry<AuthRoutes.LoginScreenRoute> {
        AuthShell(mainViewModel = mainViewModel) {
            LoginScreen(
                onLogin = { backStack.add(MainRoutes.HomeRoute) },
                onSignUp = { backStack.add(AuthRoutes.CreateAccountRoute) },
            )
        }
    }
    entry<AuthRoutes.UploadLogoRoute> {
        AuthShell(mainViewModel = mainViewModel) {
            UploadLogoScreen { }
        }
    }
}
