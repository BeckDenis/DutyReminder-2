package denis.beck.reminder

import RemindDomainModel
import denis.beck.reminder.data.room.RemindDao
import kotlinx.coroutines.flow.map

class RemindRepository(private val remindDao: RemindDao) {

    suspend fun getReminds() = remindDao.getAll().map { it.toDomain() }

    fun getRemindsByFlow() = remindDao
        .getAllByFlow()
        .map { list ->
            list.map {
                it.toDomain()
            }
        }

    suspend fun getRemind(id: Long?): RemindDomainModel? {
        id ?: return null
        return remindDao.get(id)?.toDomain()
    }

    suspend fun setRemind(remindDomainModel: RemindDomainModel) =
        remindDao.insert(remindDomainModel.toEntity())

    suspend fun deleteRemind(remindId: Long) = remindDao.delete(remindId)

    suspend fun updateRemind(remind: RemindDomainModel) = remindDao.update(remind.toEntity())
}