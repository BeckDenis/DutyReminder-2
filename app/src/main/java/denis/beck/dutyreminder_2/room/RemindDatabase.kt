package denis.beck.dutyreminder_2.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [RemindEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RemindDatabase : RoomDatabase() {
    abstract fun reminderDao(): RemindDao
}