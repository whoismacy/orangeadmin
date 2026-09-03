package com.sixteenhalves.orangeadmin.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sixteenhalves.orangeadmin.domain.MainScreenNavigationItems

@Composable
fun MainAppShell(
    backStack: MutableList<AppRoute>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val currentRoute = backStack.last()
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
        content()
    }
}
