package denis.beck.reminder_list_ui.di

import dagger.Component
import denis.beck.common.di.FragmentScope
import denis.beck.common.viewModel.ViewModelModule
import denis.beck.reminder_list_ui.ReminderListFragment

@FragmentScope
@Component(
    dependencies = [ReminderListDependencies::class],
    modules = [
        ViewModelModule::class,
        ReminderListModule::class,
    ]
)
interface ReminderListComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ReminderListDependencies
        ): ReminderListComponent
    }

    fun inject(fragment:ReminderListFragment)
}