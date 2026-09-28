package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onNewBranch: () -> Unit,
) {
    val branchesExist =
        mainViewModel
            .branchesExist
            .collectAsStateWithLifecycle()
            .value
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (branchesExist) {
            PopulatedDashboard()
        } else {
            EmptyDashboard(onNewBranch)
        }
    }
}
