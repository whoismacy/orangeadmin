package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.main.BranchesScreen
import com.sixteenhalves.orangeadmin.screens.main.CSVImportScreen
import com.sixteenhalves.orangeadmin.screens.main.HomeScreen
import com.sixteenhalves.orangeadmin.screens.main.InventoryScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsScreen
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoutes : AppRoute {
    @Serializable data object HomeRoute : MainRoutes

    @Serializable data object Inventory : MainRoutes

    @Serializable data object Branches : MainRoutes

    @Serializable data object Settings : MainRoutes

    @Serializable data object CSVImport : MainRoutes
}

fun EntryProviderScope<AppRoute>.mainGraph(
    backStack: MutableList<AppRoute>,
    mainViewModel: MainViewModel,
) {
    entry<MainRoutes.HomeRoute> {
        MainAppShell(backStack = backStack) {
            HomeScreen(
                mainViewModel = mainViewModel,
                onNewBranch =
                    { backStack.add(MainRoutes.Branches) },
            )
        }
    }

    entry<MainRoutes.Inventory> {
        MainAppShell(backStack = backStack) {
            InventoryScreen(
                mainViewModel = mainViewModel,
                onNewBranch = { backStack.add(MainRoutes.Branches) },
                onNavigateCSV = { backStack.add(MainRoutes.CSVImport) },
            )
        }
    }
    entry<MainRoutes.Branches> {
        MainAppShell(backStack = backStack) { BranchesScreen() }
    }
    entry<MainRoutes.Settings> {
        MainAppShell(backStack = backStack) { SettingsScreen() }
    }
    entry<MainRoutes.CSVImport> {
        MainAppShell(backStack = backStack) { CSVImportScreen() }
    }
}
