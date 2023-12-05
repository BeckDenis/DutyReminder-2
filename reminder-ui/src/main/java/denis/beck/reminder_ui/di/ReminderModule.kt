package denis.beck.reminder_ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import denis.beck.common.viewModel.ViewModelKey
import denis.beck.reminder_ui.ReminderViewModel

@Module
interface ReminderModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReminderViewModel::class)
    fun bindViewModel(viewModel: ReminderViewModel): ViewModel

}