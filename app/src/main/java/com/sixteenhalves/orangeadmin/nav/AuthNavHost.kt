package com.sixteenhalves.orangeadmin.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sixteenhalves.orangeadmin.screens.onboarding.AuthLandingScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyDescriptionScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CompanyNameScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.CreateAccountScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.LoginScreen
import com.sixteenhalves.orangeadmin.screens.onboarding.UploadLogoScreen

@Composable
fun AuthNavHost(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Any>(AuthRoutes.AuthLandingRoute) }
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        entryProvider =
            entryProvider {
                entry<AuthRoutes.AuthLandingRoute> {
                    AuthLandingScreen()
                }
                entry<AuthRoutes.CompanyNameRoute> {
                    CompanyNameScreen()
                }
                entry<AuthRoutes.CompanyDescriptionRoute> {
                    CompanyDescriptionScreen()
                }
                entry<AuthRoutes.CreateAccountRoute> {
                    CreateAccountScreen()
                }
                entry<AuthRoutes.LoginScreenRoute> {
                    LoginScreen()
                }
                entry<AuthRoutes.UploadLogoRoute> {
                    UploadLogoScreen()
                }
            },
    )
}
