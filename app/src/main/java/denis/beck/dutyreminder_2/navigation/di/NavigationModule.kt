package denis.beck.dutyreminder_2.navigation.di

import dagger.Binds
import dagger.Module
import denis.beck.dutyreminder_2.navigation.NavigatorImpl
import denis.beck.navigation.Navigator

@Module
interface NavigationModule {
    @Binds
    fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}