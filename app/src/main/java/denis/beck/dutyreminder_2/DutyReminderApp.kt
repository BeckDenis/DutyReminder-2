package denis.beck.dutyreminder_2

import android.app.Application
import timber.log.Timber

class DutyReminderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}