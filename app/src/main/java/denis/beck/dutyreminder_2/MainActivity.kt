package denis.beck.dutyreminder_2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import denis.beck.dutyreminder_2.di.ActivityComponent
import denis.beck.login_ui.di.LoginComponent
import denis.beck.login_ui.di.LoginComponentFactoryProvider
import denis.beck.navigation.Navigator
import denis.beck.preferences.SharedPreferencesManager
import denis.beck.reminder_list_ui.di.ReminderListComponent
import denis.beck.reminder_list_ui.di.ReminderListComponentFactoryProvider
import denis.beck.reminder_ui.di.ReminderComponent
import denis.beck.reminder_ui.di.ReminderComponentFactoryProvider
import javax.inject.Inject

class MainActivity :
    AppCompatActivity(),
    LoginComponentFactoryProvider,
    ReminderComponentFactoryProvider,
    ReminderListComponentFactoryProvider {

    private lateinit var component: ActivityComponent

    override val loginComponentFactory: LoginComponent.Factory
        get() = component.loginComponentFactory
    override val reminderComponentFactory: ReminderComponent.Factory
        get() = component.reminderComponentFactory
    override val reminderListComponentFactory: ReminderListComponent.Factory
        get() = component.reminderListComponentFactory

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (applicationContext as DutyReminderApp)
            .applicationGraph
            .activityComponentFactory
            .create()
        component.inject(this)

        setContentView(R.layout.activity_main)

        val prefs = SharedPreferencesManager(this)

        supportFragmentManager.commit {
            if (prefs.getAuthorized()) {
                navigator.navigateToMain(supportFragmentManager)
            } else {
                navigator.navigateToLogin(supportFragmentManager)
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
}