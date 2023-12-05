package denis.beck.reminder_list_ui.di

import denis.beck.navigation.Navigator
import denis.beck.reminder.data.RemindRepository

interface ReminderListDependencies {
    fun navigator(): Navigator
    fun remindRepository(): RemindRepository
}