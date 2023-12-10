package denis.beck.dutyreminder_2

import android.app.Application
import denis.beck.dutyreminder_2.di.ApplicationGraph
import denis.beck.dutyreminder_2.di.DaggerApplicationGraph
import denis.beck.notifications.notification.NotificationManagerProvider
import denis.beck.notifications.notification.RemindNotificationManager
import denis.beck.reminder.di.RemindManagerProvider
import denis.beck.reminder.domain.remindManager.RemindManager
import timber.log.Timber

class DutyReminderApp : Application(), RemindManagerProvider, NotificationManagerProvider {

    val applicationGraph: ApplicationGraph = DaggerApplicationGraph
        .factory()
        .create(this)

    override val remindManager: RemindManager
        get() = applicationGraph.remindManager()

    override val notificationManager: RemindNotificationManager
        get() = applicationGraph.notificationManager()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}