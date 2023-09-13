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
    fun insert(remind: RemindEntity) : Long

    @Delete
    fun delete(reminder: RemindEntity)

    @Query("DELETE FROM remind WHERE id = :remindId")
    fun delete(remindId: Long)
}