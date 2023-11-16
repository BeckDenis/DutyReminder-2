package denis.beck.dutyreminder_2

import android.app.Application
import denis.beck.dutyreminder_2.navigation.NavigatorImpl
import denis.beck.navigation.Navigator
import denis.beck.navigation.NavigatorSingleton
import denis.beck.reminder.RemindDatabaseSingleton
import denis.beck.reminder.data.room.RemindDatabase
import timber.log.Timber

class DutyReminderApp : Application() {

    lateinit var remindDatabase : RemindDatabase

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        RemindDatabaseSingleton.init(applicationContext)
        NavigatorSingleton.init(NavigatorImpl())
    }


}