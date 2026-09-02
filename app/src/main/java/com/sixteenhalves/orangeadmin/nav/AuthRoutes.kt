package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.onboarding.AuthLandingScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyDescriptionScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyNameScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CreateAccountScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.LoginScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.UploadLogoScreen
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
}

fun EntryProviderScope<AppRoute>.authGraph(backStack: MutableList<AppRoute>) {
    entry<AuthRoutes.AuthLandingRoute> {
        AuthLandingScreen(
            onCreateAccount = { backStack.add(AuthRoutes.CreateAccountRoute) },
            onLogin = { backStack.add(AuthRoutes.LoginScreenRoute) },
        )
    }
    entry<AuthRoutes.CompanyDescriptionRoute> {
        CompanyDescriptionScreen(onNext = { backStack.add(MainRoutes.HomeRoute) })
    }
    entry<AuthRoutes.CompanyNameRoute> {
        CompanyNameScreen(onNext = { backStack.add(AuthRoutes.CompanyDescriptionRoute) })
    }
    entry<AuthRoutes.CreateAccountRoute> {
        CreateAccountScreen(
            onNext = { backStack.add(AuthRoutes.CompanyNameRoute) },
            onSignIn = { backStack.add(AuthRoutes.CompanyNameRoute) },
        )
    }
    entry<AuthRoutes.LoginScreenRoute> {
        LoginScreen(
            onLogin = { backStack.add(MainRoutes.HomeRoute) },
            onSignUp = { backStack.add(AuthRoutes.CreateAccountRoute) },
        )
    }
    entry<AuthRoutes.UploadLogoRoute> {
        UploadLogoScreen { }
    }
}
