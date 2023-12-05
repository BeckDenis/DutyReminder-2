package denis.beck.login_ui.domain.di

import dagger.Binds
import dagger.Module
import denis.beck.common.di.FragmentScope
import denis.beck.login_ui.domain.LoginInteractor
import denis.beck.login_ui.domain.LoginInteractorImpl

@Module
internal interface LoginDomainModule {

    @Binds
    @FragmentScope
    fun bindLoginInteractor(impl: LoginInteractorImpl): LoginInteractor
}