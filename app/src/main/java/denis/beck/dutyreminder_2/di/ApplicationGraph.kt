package denis.beck.dutyreminder_2.di

import android.content.Context
import dagger.Component
import denis.beck.dutyreminder_2.navigation.di.NavigationModule
import denis.beck.navigation.Navigator
import denis.beck.reminder.data.room.RemindDao
import denis.beck.reminder.di.RemindDatabaseModule

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
            applicationContext: Context,
        ): ApplicationGraph
    }

    // fun navigator(): Navigator
    fun remindDao(): RemindDao
}