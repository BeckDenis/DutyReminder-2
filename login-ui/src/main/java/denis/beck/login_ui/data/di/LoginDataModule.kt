package denis.beck.login_ui.data.di

import dagger.Binds
import dagger.Module
import denis.beck.common.di.FragmentScope
import denis.beck.login_ui.data.LoginRepository
import denis.beck.login_ui.data.LoginRepositoryImpl
import denis.beck.login_ui.data.external.LoginExternalApi
import denis.beck.login_ui.data.external.LoginExternalApiImpl
import denis.beck.login_ui.data.local.LoginDao
import denis.beck.login_ui.data.local.LoginDatabase

@Module
internal interface LoginDataModule {

    @Binds
    @FragmentScope
    fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @FragmentScope
    fun bindLoginDao(impl: LoginDatabase): LoginDao

    @Binds
    @FragmentScope
    fun bindLoginExternalApi(impl: LoginExternalApiImpl): LoginExternalApi
}