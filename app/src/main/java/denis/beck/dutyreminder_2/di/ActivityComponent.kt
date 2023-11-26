package denis.beck.dutyreminder_2.di

import dagger.Component
import denis.beck.common.di.ActivityScope
import denis.beck.login_ui.di.LoginDependencies
import denis.beck.reminder_list_ui.di.ReminderListDependencies
import denis.beck.reminder_ui.di.ReminderDependencies

@ActivityScope
@Component(
    dependencies = [ApplicationGraph::class],
)
interface ActivityComponent :
    LoginDependencies,
    ReminderDependencies,
    ReminderListDependencies {

    @Component.Factory
    interface Factory {
        fun create(
            applicationGraph: ApplicationGraph
        ): ActivityComponent
    }
}