package denis.beck.common_core.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Module
import dagger.Provides

@Module
object ViewModelModule {

    @Provides
    fun provideViewModelProvider(viewModelStore: ViewModelStore, factory: ViewModelFactory): ViewModelProvider {
        return ViewModelProvider(
            store = viewModelStore,
            factory = factory,
        )
    }
}