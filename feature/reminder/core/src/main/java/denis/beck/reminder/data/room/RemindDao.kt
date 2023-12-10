package denis.beck.reminder.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindDao {
    @Query("SELECT * FROM remind")
    suspend fun getAll(): List<RemindEntity>

    @Query("SELECT * FROM remind")
    fun getAllByFlow(): Flow<List<RemindEntity>>

    @Query("SELECT * FROM remind WHERE id = :remindId")
    suspend fun get(remindId: Long): RemindEntity?

    @Insert
    suspend fun insert(remind: RemindEntity) : Long

    @Delete
    suspend fun delete(reminder: RemindEntity)

    @Query("DELETE FROM remind WHERE id = :remindId")
    suspend fun delete(remindId: Long)

    @Update
    suspend fun update(remind: RemindEntity)
}