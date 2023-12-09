package denis.beck.reminder_ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import denis.beck.common.viewModel.ViewModelKey
import denis.beck.reminder_ui.ReminderViewModel

@Module()
interface ReminderViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReminderViewModel::class)
    fun bindViewModel(viewModel: ReminderViewModel): ViewModel

}