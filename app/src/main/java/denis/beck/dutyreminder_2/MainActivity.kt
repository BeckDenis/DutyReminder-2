package denis.beck.dutyreminder_2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import denis.beck.dutyreminder_2.di.DaggerActivityComponent
import denis.beck.login_ui.di.LoginDependencies
import denis.beck.login_ui.di.LoginDependenciesProvider
import denis.beck.preferences.SharedPreferencesManager

class MainActivity :
    AppCompatActivity(),
    LoginDependenciesProvider {

    private val component = DaggerActivityComponent.factory().create((applicationContext as DutyReminderApp).applicationGraph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = SharedPreferencesManager(this)

        supportFragmentManager.commit {
            if (prefs.getAuthorized()) {
                // add<MainFragment>(R.id.fragment_container)
            } else {
                component.navigator().navigateToLogin(supportFragmentManager)
            }
            addToBackStack(null)
        }

        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val grant = ContextCompat.checkSelfPermission(this, permission)
            if (grant != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(permission), 0)
            }
        }
    }

    override fun loginDependencies(): LoginDependencies = component
}