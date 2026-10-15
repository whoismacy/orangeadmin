package com.sixteenhalves.orangeadmin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixteenhalves.orangeadmin.nav.AppNav
import com.sixteenhalves.orangeadmin.ui.theme.OrangeAdminTheme
import com.sixteenhalves.orangeadmin.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreference by mainViewModel.themePreference.collectAsStateWithLifecycle()

            OrangeAdminTheme(themePreference = themePreference) {
                AppNav(mainViewModel = mainViewModel)
            }
        }
    }
}
