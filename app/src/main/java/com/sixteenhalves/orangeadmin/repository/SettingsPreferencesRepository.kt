package com.sixteenhalves.orangeadmin.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sixteenhalves.orangeadmin.domain.NotificationPreferences
import com.sixteenhalves.orangeadmin.domain.ThemePreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings_preferences")

@Singleton
class SettingsPreferencesRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val THEME_KEY = stringPreferencesKey("theme_preference")
        private val LOW_STOCK_ALERTS_KEY = booleanPreferencesKey("low_stock_alerts")

        val themePreference: Flow<ThemePreference> =
            context.dataStore.data.map { preferences ->
                val themeString = preferences[THEME_KEY] ?: ThemePreference.SYSTEM_DEFAULT.name
                ThemePreference.fromString(themeString)
            }

        val notificationPreferences: Flow<NotificationPreferences> =
            context.dataStore.data.map { preferences ->
                NotificationPreferences(
                    lowStockAlerts = preferences[LOW_STOCK_ALERTS_KEY] ?: false,
                )
            }

        suspend fun updateThemePreference(themePreference: ThemePreference) {
            context.dataStore.edit { preferences ->
                preferences[THEME_KEY] = themePreference.name
            }
        }

        suspend fun updateNotificationPreference(lowStockAlerts: Boolean? = null) {
            context.dataStore.edit { preferences ->
                lowStockAlerts?.let { preferences[LOW_STOCK_ALERTS_KEY] = it }
            }
        }
    }
