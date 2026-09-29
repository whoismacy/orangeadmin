package com.sixteenhalves.orangeadmin.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.domain.MainScreenNavigationItems
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel

@Composable
fun MainAppShell(
    backStack: MutableList<AppRoute>,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val currentRoute = backStack.last()
    val snackbarHostState = mainViewModel.snackbarHostState

    LaunchedEffect(Unit) {
        EventManager.channelFlow.collect { event ->
            when (event) {
                is EventManager.AppEvent.ShowEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            MainScreenNavigationItems.entries.forEach {
                item(
                    onClick = { backStack.add(it.route) },
                    label = { Text(it.title) },
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = null,
                        )
                    },
                    selected = it.route == currentRoute,
                )
            }
        },
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}
