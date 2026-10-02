package com.sixteenhalves.orangeadmin.domain

import com.sixteenhalves.orangeadmin.R
import com.sixteenhalves.orangeadmin.nav.AppRoute
import com.sixteenhalves.orangeadmin.nav.BranchRoutes
import com.sixteenhalves.orangeadmin.nav.MainRoutes
import com.sixteenhalves.orangeadmin.nav.SettingsRoutes

enum class MainScreenNavigationItems(
    val title: String,
    val icon: Int,
    val route: AppRoute,
) {
    Dashboard(
        "Dashboard",
        R.drawable.baseline_space_dashboard_24,
        MainRoutes.HomeRoute,
    ),
    Inventory(
        "Inventory",
        R.drawable.baseline_inventory_2_24,
        MainRoutes.Inventory,
    ),
    Branches(
        "Branches",
        R.drawable.outline_call_split_24,
        BranchRoutes.Home,
    ),
    Settings(
        "Settings",
        R.drawable.outline_settings_24,
        SettingsRoutes.Home,
    ),
}
