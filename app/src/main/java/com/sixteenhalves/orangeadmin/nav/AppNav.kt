package com.sixteenhalves.orangeadmin.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel

@Composable
fun AppNav(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val isLoggedIn = authViewModel.isLoggedIn.collectAsStateWithLifecycle().value
    val backStack = remember { mutableStateListOf(if (isLoggedIn) MainRoutes.HomeRoute else AuthRoutes.AuthLandingRoute) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider =
            entryProvider {
                mainGraph(backStack, mainViewModel = mainViewModel)
                authGraph(backStack, mainViewModel = mainViewModel, authViewModel = authViewModel)
            },
    )
}
