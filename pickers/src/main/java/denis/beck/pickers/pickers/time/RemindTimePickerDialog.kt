package denis.beck.pickers.pickers.time

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.Calendar

class RemindTimePickerDialog : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager) = RemindTimePickerDialog().show(fragmentManager, "timePicker")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, parentFragment as OnTimeSetListener, hour, minute, DateFormat.is24HourFormat(activity))
    }
}