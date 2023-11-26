package denis.beck.dutyreminder_2

import android.app.Application
import denis.beck.dutyreminder_2.di.ApplicationGraph
import denis.beck.dutyreminder_2.di.DaggerApplicationGraph
import denis.beck.reminder.data.room.RemindDao
import denis.beck.reminder.di.ReminderDatabaseProvider
import timber.log.Timber

class DutyReminderApp : Application(), ReminderDatabaseProvider {

    val applicationGraph: ApplicationGraph = DaggerApplicationGraph.create()

    override val remindDao: RemindDao
        get() = applicationGraph.remindDao()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}