package denis.beck.dutyreminder_2.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.time.Duration
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.DurationUnit

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}

fun Calendar.toDateAndTimeString(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH).toDateAndTimeString()
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH).toDateAndTimeString()
    val hoursOfDay = this.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()
    val minute = this.get(Calendar.MINUTE).toDateAndTimeString()

    return "$hoursOfDay:$minute\n$dayOfMonth.$month.$year"
}

fun Calendar.toDateAndTimeLogString(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH).toDateAndTimeString()
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH).toDateAndTimeString()
    val hoursOfDay = this.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()
    val minute = this.get(Calendar.MINUTE).toDateAndTimeString()

    return "$hoursOfDay:$minute $dayOfMonth.$month.$year"
}

fun Int.toDateAndTimeString() : String = if (this >= 9) this.toString() else "0$this"
fun Duration.toDateAndTimeString(unit: DurationUnit) : String = this.toInt(unit).toDateAndTimeString()

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}