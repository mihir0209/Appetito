package ui

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// Define the DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class Theme {
    Light, Dark, System
}

class ThemeViewModel(context: Context) : ViewModel() {
    private val dataStore = context.dataStore
    private val themeKey = stringPreferencesKey("theme")

    // This state will be observed by the UI
    val theme = mutableStateOf(Theme.System)

    init {
        viewModelScope.launch {
            dataStore.data.map { preferences ->
                Theme.valueOf(preferences[themeKey] ?: Theme.System.name)
            }.collect {
                theme.value = it
            }
        }
    }

    fun setTheme(newTheme: Theme) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[themeKey] = newTheme.name
            }
        }
    }
}