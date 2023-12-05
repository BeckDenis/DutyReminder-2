package denis.beck.login_ui.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import denis.beck.common.viewModel.ViewModelKey
import denis.beck.login_ui.presentation.LoginViewModel
import denis.beck.login_ui.presentation.LoginViewModelImpl

@Module
interface LoginPresentationModule {

    @Module
    companion object {
        @Provides
        fun provideViewModel(viewModelProvider: ViewModelProvider): LoginViewModel {
            return viewModelProvider[LoginViewModelImpl::class.java]
        }
    }

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModelImpl::class)
    fun bindViewModel(viewModel: LoginViewModelImpl): ViewModel
}