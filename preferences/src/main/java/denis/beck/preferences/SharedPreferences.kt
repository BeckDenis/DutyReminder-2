package denis.beck.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesManager(context: Context) {

    companion object {
        private const val AUTHORIZED_PREF_KEY = "authorized.pref.key"
    }

    private val sharedPreferences: SharedPreferences by lazy { context.getSharedPreferences("prefs", Context.MODE_PRIVATE) }


    fun setAuthorized(value: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(AUTHORIZED_PREF_KEY, value)
        }
    }

    fun getAuthorized(): Boolean {
        return sharedPreferences.getBoolean(AUTHORIZED_PREF_KEY, false)
    }


}