package denis.beck.dutyreminder_2.room

import androidx.room.TypeConverter
import denis.beck.dutyreminder_2.epoxy.models.DayOfWeek

class Converters {
    @TypeConverter
    fun listToJsonString(value: Set<DayOfWeek>): String = value.joinToString(separator = ",")

    @TypeConverter
    fun jsonStringToList(value: String): Set<DayOfWeek> = if (value.isBlank()) {
        emptySet()
    } else {
        value.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    }
}