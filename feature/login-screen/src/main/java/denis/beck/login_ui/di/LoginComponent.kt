package denis.beck.login_ui.di

import android.content.Context
import androidx.lifecycle.ViewModelStore
import dagger.BindsInstance
import dagger.Component
import denis.beck.common_core.di.FragmentScope
import denis.beck.common_core.viewModel.ViewModelModule
import denis.beck.login_ui.data.di.LoginDataModule
import denis.beck.login_ui.domain.di.LoginDomainModule
import denis.beck.login_ui.presentation.LoginFragment
import denis.beck.login_ui.presentation.di.LoginPresentationModule

@FragmentScope
@Component(
    dependencies = [LoginDependencies::class],
    modules = [
        ViewModelModule::class,
        LoginDataModule::class,
        LoginDomainModule::class,
        LoginPresentationModule::class,
    ],
)
interface LoginComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance viewModelStore: ViewModelStore,
            advertiserInfoExternalDependencies: LoginDependencies,
        ): LoginComponent
    }

    fun inject(fragment: LoginFragment)

}

