package denis.beck.dutyreminder_2.di

import dagger.Component
import denis.beck.common.di.ActivityScope
import denis.beck.login_ui.di.LoginDependencies

@ActivityScope
@Component(
    dependencies = [ApplicationGraph::class],
)
interface ActivityComponent: LoginDependencies {

    @Component.Factory
    interface Factory {
        fun create(
            applicationGraph: ApplicationGraph
        ): ActivityComponent
    }

}