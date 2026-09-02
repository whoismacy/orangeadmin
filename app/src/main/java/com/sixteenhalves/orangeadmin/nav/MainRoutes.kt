package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.main.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoutes : AppRoute {
    @Serializable data object HomeRoute : MainRoutes
}

fun EntryProviderScope<AppRoute>.mainGraph(backStack: MutableList<AppRoute>) {
    entry<MainRoutes.HomeRoute> {
        HomeScreen()
    }
}
