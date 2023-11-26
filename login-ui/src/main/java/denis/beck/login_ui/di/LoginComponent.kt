package denis.beck.login_ui.di

import dagger.Component
import denis.beck.common.di.ActivityScope
import denis.beck.login_ui.data.di.LoginDataModule
import denis.beck.login_ui.presentation.LoginFragment

@ActivityScope
@Component(
    dependencies = [LoginDependencies::class],
    modules = [LoginDataModule::class],
)
interface LoginComponent {

    @Component.Factory
    interface Factory {
        fun create(
            advertiserInfoExternalDependencies: LoginDependencies,
        ): LoginComponent
    }

    fun inject(fragment: LoginFragment)

}

