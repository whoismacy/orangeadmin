package com.sixteenhalves.orangeadmin.nav

import androidx.navigation3.runtime.EntryProviderScope
import com.sixteenhalves.orangeadmin.screens.main.BranchDetailScreen
import com.sixteenhalves.orangeadmin.screens.main.BranchDeviceScreen
import com.sixteenhalves.orangeadmin.screens.main.BranchLocationScreen
import com.sixteenhalves.orangeadmin.screens.main.BranchNameScreen
import com.sixteenhalves.orangeadmin.screens.main.BranchesScreen
import com.sixteenhalves.orangeadmin.screens.main.HomeScreen
import com.sixteenhalves.orangeadmin.screens.main.InventoryScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsAppDetailsScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsBiometricsScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsNotificationsScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsScreen
import com.sixteenhalves.orangeadmin.screens.main.SettingsThemeScreen
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoutes : AppRoute {
    @Serializable data object HomeRoute : MainRoutes

    @Serializable data object Inventory : MainRoutes

    @Serializable data object Branches : MainRoutes

    @Serializable data object Settings : MainRoutes
}

@Serializable
sealed interface BranchRoutes : AppRoute {
    @Serializable data object Home : BranchRoutes

    @Serializable data object Name : BranchRoutes

    @Serializable data object Location : BranchRoutes

    @Serializable data object Device : BranchRoutes

    @Serializable data class Detail(
        val branchId: String,
    ) : BranchRoutes
}

@Serializable
sealed interface SettingsRoutes : AppRoute {
    @Serializable data object Home : SettingsRoutes

    @Serializable data object Theme : SettingsRoutes

    @Serializable data object Biometrics : SettingsRoutes

    @Serializable data object Notifications : SettingsRoutes

    @Serializable data object AppDetails : SettingsRoutes
}

fun EntryProviderScope<AppRoute>.mainGraph(
    backStack: MutableList<AppRoute>,
    mainViewModel: MainViewModel,
) {
    entry<MainRoutes.HomeRoute> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            HomeScreen(
                mainViewModel = mainViewModel,
                onNewBranch =
                    { backStack.add(BranchRoutes.Name) },
            )
        }
    }

    entry<MainRoutes.Inventory> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            InventoryScreen(
                mainViewModel = mainViewModel,
                onNewBranch = { backStack.add(BranchRoutes.Name) },
            )
        }
    }

    entry<BranchRoutes.Home> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            BranchesScreen(
                onNewBranch = { backStack.add(BranchRoutes.Name) },
                onBranchClick = { branchId -> backStack.add(BranchRoutes.Detail(branchId)) },
            )
        }
    }
    entry<BranchRoutes.Name> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            BranchNameScreen(
                onNext = { _ ->
                    backStack.add(BranchRoutes.Location)
                },
            )
        }
    }
    entry<BranchRoutes.Location> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            BranchLocationScreen(
                onComplete = {
                    backStack.removeLastOrNull()
                    backStack.removeLastOrNull()
                    // I should trigger an api call that adds the new location
                    // then either with the viewmodel after receiving the new branch_id from
                    // the server add to the navigation stack the new location
                    // or handle the error gracefully
                    backStack.add(BranchRoutes.Detail("NEW_BRANCH_ID"))
                },
            )
        }
    }
    entry<BranchRoutes.Device> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            BranchDeviceScreen(
                onSave = {
                    // I should trigger an API call that registers the device and refreshes the stale pos device data
                    // then return to the detail screen
                    backStack.removeLastOrNull()
                },
            )
        }
    }
    entry<BranchRoutes.Detail> { route ->
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            BranchDetailScreen(
                branchId = route.branchId,
                onAddDevice = { backStack.add(BranchRoutes.Device) },
                onBack = { backStack.removeLastOrNull() },
            )
        }
    }
    entry<SettingsRoutes.Home> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) {
            SettingsScreen(
                onNavigateTheme = { backStack.add(SettingsRoutes.Theme) },
                onNavigateBiometrics = { backStack.add(SettingsRoutes.Biometrics) },
                onNavigateNotifications = { backStack.add(SettingsRoutes.Notifications) },
                onNavigateAppDetails = { backStack.add(SettingsRoutes.AppDetails) },
            )
        }
    }
    entry<SettingsRoutes.Theme> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) { SettingsThemeScreen(mainViewModel = mainViewModel) }
    }
    entry<SettingsRoutes.Biometrics> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) { SettingsBiometricsScreen(mainViewModel = mainViewModel) }
    }
    entry<SettingsRoutes.Notifications> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) { SettingsNotificationsScreen(mainViewModel = mainViewModel) }
    }
    entry<SettingsRoutes.AppDetails> {
        MainAppShell(backStack = backStack, mainViewModel = mainViewModel) { SettingsAppDetailsScreen() }
    }
}
