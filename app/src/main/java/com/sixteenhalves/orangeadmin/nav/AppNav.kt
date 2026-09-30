package com.sixteenhalves.orangeadmin.nav

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
        transitionSpec = {
            (
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> (fullWidth * 0.10f).toInt() },
                    animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing),
                ) + fadeIn(animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing))
            ) togetherWith (
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> (-fullWidth * 0.10f).toInt() },
                    animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing),
                ) + fadeOut(animationSpec = tween(durationMillis = 180, easing = FastOutLinearInEasing))
            )
        },
        popTransitionSpec = {
            (
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> (-fullWidth * 0.10f).toInt() },
                    animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing),
                ) + fadeIn(animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing))
            ) togetherWith (
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> (fullWidth * 0.10f).toInt() },
                    animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing),
                ) + fadeOut(animationSpec = tween(durationMillis = 180, easing = FastOutLinearInEasing))
            )
        },
        predictivePopTransitionSpec = { _ ->
            (
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> (-fullWidth * 0.10f).toInt() },
                    animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing),
                ) + fadeIn(animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing)) +
                    scaleIn(
                        initialScale = 0.95f,
                        animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing),
                    )
            ) togetherWith (
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> (fullWidth * 0.10f).toInt() },
                    animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing),
                ) + fadeOut(animationSpec = tween(durationMillis = 180, easing = FastOutLinearInEasing)) +
                    scaleOut(
                        targetScale = 0.92f,
                        animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing),
                    )
            )
        },
    )
}
