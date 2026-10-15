package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel

@Composable
fun InventoryScreen(
    onNewBranch: () -> Unit,
    mainViewModel: MainViewModel,
) {
    val branchesExist =
        mainViewModel
            .branchesExist
            .collectAsStateWithLifecycle()
            .value
    if (branchesExist) {
        PopulatedInventory()
    } else {
//        PopulatedInventory()
        EmptyInventory(onNewBranch)
    }
}
