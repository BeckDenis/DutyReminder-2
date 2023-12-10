package denis.beck.reminder_ui.di

import android.content.Context
import androidx.lifecycle.ViewModelStore
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import denis.beck.common.di.FragmentScope
import denis.beck.common.viewModel.ViewModelModule
import denis.beck.reminder.di.RemindDatabaseModule
import denis.beck.reminder_ui.ReminderFragment
import javax.inject.Qualifier
import javax.inject.Scope

@FragmentScope
@Subcomponent(
    modules = [
        ReminderViewModelModule::class,
    ]
)
interface ReminderComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance @RemindId remindId: Long?,
        ): ReminderComponent
    }

    fun inject(fragment: ReminderFragment)

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemindId

@Module(subcomponents = [ReminderComponent::class])
class ReminderModule