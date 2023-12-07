package denis.beck.reminder_ui.di

import android.content.Context
import androidx.lifecycle.ViewModelStore
import dagger.BindsInstance
import dagger.Component
import denis.beck.common.di.FragmentScope
import denis.beck.common.viewModel.ViewModelModule
import denis.beck.reminder.di.RemindDatabaseModule
import denis.beck.reminder_ui.ReminderFragment
import javax.inject.Qualifier
import javax.inject.Scope

@FragmentScope
@Component(
    dependencies = [ReminderDependencies::class],
    modules = [
        ViewModelModule::class,
        ReminderModule::class,
    ]
)
interface ReminderComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance @RemindId remindId: Long?,
            dependencies: ReminderDependencies,
        ): ReminderComponent
    }

    fun inject(fragment: ReminderFragment)

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemindId