package denis.beck.login_ui.domain.di

import dagger.Binds
import dagger.Module
import denis.beck.login_ui.domain.LoginInteractor
import denis.beck.login_ui.domain.LoginInteractorImpl

@Module
internal interface LoginDomainModule {

    @Binds
    fun bindLoginInteractor(impl: LoginInteractorImpl): LoginInteractor
}