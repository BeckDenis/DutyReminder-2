package denis.beck.dutyreminder_2.di

import dagger.Module
import dagger.Subcomponent
import denis.beck.common.di.ActivityScope
import denis.beck.dutyreminder_2.MainActivity
import denis.beck.login_ui.di.LoginComponent
import denis.beck.login_ui.di.LoginModule
import denis.beck.reminder_list_ui.di.ReminderListComponent
import denis.beck.reminder_list_ui.di.ReminderListModule
import denis.beck.reminder_ui.di.ReminderComponent
import denis.beck.reminder_ui.di.ReminderModule
import denis.beck.reminder_ui.di.ReminderViewModelModule

@ActivityScope
@Subcomponent(
    modules = [ReminderListModule::class, ReminderModule::class, LoginModule::class]
)
interface ActivityComponent {

    val reminderListComponentFactory: ReminderListComponent.Factory
    val reminderComponentFactory: ReminderComponent.Factory
    val loginComponentFactory: LoginComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun inject(activity: MainActivity)
}

@Module(subcomponents = [ActivityComponent::class])
class ActivityModule