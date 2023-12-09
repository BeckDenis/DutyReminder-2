package denis.beck.reminder_list_ui.di

import dagger.Subcomponent
import denis.beck.common.di.FragmentScope
import denis.beck.reminder_list_ui.ReminderListFragment

@FragmentScope
@Subcomponent
interface ReminderListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ReminderListComponent
    }

    fun inject(fragment:ReminderListFragment)
}