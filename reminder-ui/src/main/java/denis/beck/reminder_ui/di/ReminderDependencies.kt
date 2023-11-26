package denis.beck.reminder_ui.di

import denis.beck.navigation.Navigator
import denis.beck.reminder.data.room.RemindDao

interface ReminderDependencies {
    fun navigator(): Navigator
    fun remindDao(): RemindDao
}