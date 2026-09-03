package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.main.BranchesScreen
import com.sixteenhalves.orangeadmin.screens.main.HomeScreen
import com.sixteenhalves.orangeadmin.screens.main.InventoryScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoutes : AppRoute {
    @Serializable data object HomeRoute : MainRoutes

    @Serializable data object Inventory : MainRoutes

    @Serializable data object Branches : MainRoutes

    @Serializable data object Settings : MainRoutes
}

fun EntryProviderScope<AppRoute>.mainGraph(backStack: MutableList<AppRoute>) {
    entry<MainRoutes.HomeRoute> {
        MainAppShell(backStack = backStack) { HomeScreen() }
    }

    entry<MainRoutes.Inventory> {
        MainAppShell(backStack = backStack) { InventoryScreen() }
    }
    entry<MainRoutes.Branches> {
        MainAppShell(backStack = backStack) { BranchesScreen() }
    }
    entry<MainRoutes.Settings> {
        MainAppShell(backStack = backStack) { SettingsScreen() }
    }
}
