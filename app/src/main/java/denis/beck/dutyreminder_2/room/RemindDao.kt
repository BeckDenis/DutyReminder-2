package denis.beck.dutyreminder_2.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RemindDao {
    @Query("SELECT * FROM remind")
    fun getAll(): List<RemindEntity>

    @Insert
    fun insertAll(vararg reminders: RemindEntity)

    @Delete
    fun delete(reminder: RemindEntity)
}