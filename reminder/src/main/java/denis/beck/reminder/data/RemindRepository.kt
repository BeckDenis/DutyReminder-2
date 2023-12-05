package denis.beck.reminder.data

import denis.beck.reminder.domain.remindManager.RemindDomainModel
import denis.beck.reminder.data.room.RemindDao
import denis.beck.reminder.data.room.RemindEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Мб интерфейс?
 */
class RemindRepository @Inject constructor(
    private val remindDao: RemindDao
) {

    suspend fun getReminds() = remindDao.getAll().map { it.toDomain() }

    fun getRemindsByFlow(): Flow<List<RemindEntity>> = remindDao
        .getAllByFlow()

    suspend fun getRemind(id: Long?): RemindDomainModel? {
        id ?: return null
        return remindDao.get(id)?.toDomain()
    }

    suspend fun setRemind(remindDomainModel: RemindDomainModel) =
        remindDao.insert(remindDomainModel.toEntity())

    suspend fun deleteRemind(remindId: Long) = remindDao.delete(remindId)

    suspend fun updateRemind(remind: RemindDomainModel) = remindDao.update(remind.toEntity())
}