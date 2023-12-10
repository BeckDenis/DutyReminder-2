package denis.beck.reminder_ui.di

import denis.beck.navigation.Navigator
import denis.beck.reminder.data.RemindRepository
import denis.beck.reminder.domain.remindManager.RemindManager

interface ReminderDependencies {
    fun navigator(): Navigator
    fun remindRepository(): RemindRepository
    fun remindManager(): RemindManager
}