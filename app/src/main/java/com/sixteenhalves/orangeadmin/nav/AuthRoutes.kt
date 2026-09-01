package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AuthRoutes {
    @Serializable
    data object AuthLandingRoute : AuthRoutes, NavKey

    @Serializable
    data object CompanyDescriptionRoute : AuthRoutes, NavKey

    @Serializable
    data object CompanyNameRoute : AuthRoutes, NavKey

    @Serializable
    data object CreateAccountRoute : AuthRoutes, NavKey

    @Serializable
    data object LoginScreenRoute : AuthRoutes, NavKey

    @Serializable
    data object UploadLogoRoute : AuthRoutes, NavKey
}
