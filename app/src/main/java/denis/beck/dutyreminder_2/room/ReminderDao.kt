package denis.beck.dutyreminder_2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAll(): List<ReminderEntity>

    @Insert
    fun insertAll(vararg reminders: ReminderEntity)

    @Delete
    fun delete(reminder: ReminderEntity)
}