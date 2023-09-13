package denis.beck.dutyreminder_2.room

import androidx.room.Database

@Database(entities = [ReminderEntity::class], version = 1)
abstract class ReminderDatabase {
    abstract fun reminderDao(): ReminderDao
}