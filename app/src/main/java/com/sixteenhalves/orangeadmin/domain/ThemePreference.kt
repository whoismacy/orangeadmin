package com.sixteenhalves.orangeadmin.domain

enum class ThemePreference {
    SYSTEM_DEFAULT,
    LIGHT,
    DARK,
    ;

    companion object {
        fun fromString(value: String): ThemePreference = entries.find { it.name == value } ?: SYSTEM_DEFAULT
    }
}
