package com.sixteenhalves.orangeadmin.domain

import com.sixteenhalves.orangeadmin.R
import com.sixteenhalves.orangeadmin.nav.MainRoutes

enum class MainScreenNavigationItems(
    val title: String,
    val icon: Int,
    val route: MainRoutes,
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
        MainRoutes.Branches,
    ),
    Settings(
        "Settings",
        R.drawable.outline_settings_24,
        MainRoutes.Settings,
    ),
}
