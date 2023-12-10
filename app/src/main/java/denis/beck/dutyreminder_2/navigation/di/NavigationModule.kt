package denis.beck.dutyreminder_2.navigation.di

import dagger.Binds
import dagger.Module
import denis.beck.dutyreminder_2.navigation.NavigatorImpl
import denis.beck.navigation.Navigator
import javax.inject.Singleton

@Module
interface NavigationModule {
    @Singleton
    @Binds
    fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}