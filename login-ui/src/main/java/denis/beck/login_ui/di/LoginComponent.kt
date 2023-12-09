package denis.beck.login_ui.di

import androidx.lifecycle.ViewModelStore
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import denis.beck.common.di.FragmentScope
import denis.beck.common.viewModel.ViewModelModule
import denis.beck.login_ui.data.di.LoginDataModule
import denis.beck.login_ui.domain.di.LoginDomainModule
import denis.beck.login_ui.presentation.LoginFragment
import denis.beck.login_ui.presentation.di.LoginPresentationModule

@FragmentScope
@Subcomponent(
    modules = [
        ViewModelModule::class,
        LoginDataModule::class,
        LoginDomainModule::class,
        LoginPresentationModule::class,
    ],
)
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance viewModelStore: ViewModelStore,
        ): LoginComponent
    }

    fun inject(fragment: LoginFragment)

}

@Module(subcomponents = [LoginComponent::class])
class LoginModule

