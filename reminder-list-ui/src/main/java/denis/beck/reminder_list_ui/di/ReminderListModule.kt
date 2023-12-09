package denis.beck.reminder_list_ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import denis.beck.common.di.FragmentScope
import denis.beck.common.viewModel.ViewModelKey
import denis.beck.reminder_list_ui.ReminderListViewModel
import denis.beck.reminder_list_ui.epoxy.RemindController

@Module(subcomponents = [ReminderListComponent::class])
interface ReminderListModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReminderListViewModel::class)
    fun bindViewModel(viewModel: ReminderListViewModel): ViewModel

}