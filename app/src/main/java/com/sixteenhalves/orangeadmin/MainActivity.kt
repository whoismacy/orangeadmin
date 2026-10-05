package com.sixteenhalves.orangeadmin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.sixteenhalves.orangeadmin.nav.AppNav
import com.sixteenhalves.orangeadmin.ui.theme.OrangeAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrangeAdminTheme {
                AppNav()
            }
        }
    }
}
/*
compressing and sending the register data as multipart
Handling errors gracefully throughout the application

remainder;
updating the api calls change image; change name;
apis for inventory; restocking; adding a new item; create a class for what an item should look like / done by server;
api for creating a new branch; name, location, pos-devices; active; sale-metrics
add biometric functionality api
add theme functionality api
add notification functionality
*/
