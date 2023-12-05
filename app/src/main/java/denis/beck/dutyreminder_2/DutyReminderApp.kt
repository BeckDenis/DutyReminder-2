package denis.beck.dutyreminder_2

import android.app.Application
import android.app.NotificationManager
import denis.beck.dutyreminder_2.di.ApplicationGraph
import denis.beck.dutyreminder_2.di.DaggerApplicationGraph
import denis.beck.notifications.notification.NotificationManagerProvider
import denis.beck.notifications.notification.RemindNotificationManager
import denis.beck.reminder.data.room.RemindDao
import denis.beck.reminder.di.ReminderDatabaseProvider
import timber.log.Timber

class DutyReminderApp : Application(), ReminderDatabaseProvider, NotificationManagerProvider {

    val applicationGraph: ApplicationGraph = DaggerApplicationGraph
        .factory()
        .create(this)

    override val remindDao: RemindDao
        get() = applicationGraph.remindDao()

    override val notificationManager: RemindNotificationManager
        get() = applicationGraph.notificationManager()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}