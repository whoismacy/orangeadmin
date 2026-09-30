package com.sixteenhalves.orangeadmin.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixteenhalves.orangeadmin.components.shared.LoadingSpinner
import com.sixteenhalves.orangeadmin.domain.AuthApplicationState
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel

@Composable
fun AuthShell(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel,
    content: @Composable () -> Unit,
) {
    val snackbarHostState = mainViewModel.snackbarHostState
    val authApplicationState =
        authViewModel
            .authApplicationState
            .collectAsStateWithLifecycle()
            .value

    LaunchedEffect(Unit) {
        EventManager.channelFlow.collect { event ->
            when (event) {
                is EventManager.AppEvent.ShowEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) { innerPadding ->
        when (authApplicationState) {
            is AuthApplicationState.Loading -> {
                LoadingSpinner()
            }

            else -> {
                Box(Modifier.padding(innerPadding)) {
                    content()
                }
            }
        }
    }
}
