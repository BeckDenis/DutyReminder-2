package denis.beck.reminder.di

import denis.beck.reminder.data.room.RemindDao

interface ReminderDatabaseProvider {
    val remindDao: RemindDao
}