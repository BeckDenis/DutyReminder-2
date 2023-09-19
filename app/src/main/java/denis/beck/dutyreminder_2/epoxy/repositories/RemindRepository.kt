package denis.beck.dutyreminder_2.epoxy.repositories

import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.room.RemindDao
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