package denis.beck.reminder.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RemindEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RemindDatabase : RoomDatabase() {
    abstract fun reminderDao(): RemindDao
}