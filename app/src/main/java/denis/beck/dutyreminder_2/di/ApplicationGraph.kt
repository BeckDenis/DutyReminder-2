package denis.beck.dutyreminder_2.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import denis.beck.dutyreminder_2.navigation.di.NavigationModule
import denis.beck.dutyreminder_2.reboot.RebootReceiver
import denis.beck.navigation.Navigator
import denis.beck.notifications.notification.RemindNotificationManager
import denis.beck.reminder.RemindReceiver
import denis.beck.reminder.data.RemindRepository
import denis.beck.reminder.data.room.RemindDao
import denis.beck.reminder.di.RemindDatabaseModule
import denis.beck.reminder.domain.remindManager.RemindManager
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        RemindDatabaseModule::class,
    ]
)
interface ApplicationGraph {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): ApplicationGraph
    }

    fun inject(rebootReceiver: RebootReceiver)

    fun navigator(): Navigator
    fun remindManager(): RemindManager
    fun remindRepository(): RemindRepository
    fun notificationManager(): RemindNotificationManager
}