package denis.beck.reminder_ui.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import denis.beck.common_core.di.FragmentScope
import denis.beck.common_core.viewModel.ViewModelModule
import denis.beck.reminder_ui.ReminderFragment
import javax.inject.Qualifier

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