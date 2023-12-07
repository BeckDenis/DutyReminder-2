package denis.beck.reminder.di

import denis.beck.reminder.domain.remindManager.RemindManager

interface RemindManagerProvider {
    val remindManager: RemindManager
}