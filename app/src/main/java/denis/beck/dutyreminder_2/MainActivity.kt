package denis.beck.dutyreminder_2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import denis.beck.dutyreminder_2.di.ActivityComponent
import denis.beck.dutyreminder_2.di.DaggerActivityComponent
import denis.beck.login_ui.di.LoginDependencies
import denis.beck.login_ui.di.LoginDependenciesProvider
import denis.beck.preferences.SharedPreferencesManager
import denis.beck.reminder_list_ui.di.ReminderListDependencies
import denis.beck.reminder_list_ui.di.ReminderListDependenciesProvider
import denis.beck.reminder_ui.di.ReminderDependencies
import denis.beck.reminder_ui.di.ReminderDependenciesProvider

class MainActivity :
    AppCompatActivity(),
    LoginDependenciesProvider,
    ReminderDependenciesProvider,
    ReminderListDependenciesProvider {

    private val component: ActivityComponent by lazy {
        DaggerActivityComponent
            .factory()
            .create(
                (applicationContext as DutyReminderApp)
                    .applicationGraph
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = SharedPreferencesManager(this)

        supportFragmentManager.commit {
            if (prefs.getAuthorized()) {
                component.navigator().navigateToMain(supportFragmentManager)
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
    override fun reminderDependencies(): ReminderDependencies = component
    override fun reminderListDependencies(): ReminderListDependencies = component
}