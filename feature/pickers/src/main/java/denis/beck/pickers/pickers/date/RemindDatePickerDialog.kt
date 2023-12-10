package denis.beck.pickers.pickers.date

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.Calendar

class RemindDatePickerDialog : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager) = RemindDatePickerDialog().show(fragmentManager, "datePicker")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of TimePickerDialog and return it
        return DatePickerDialog(requireContext(), parentFragment as OnDateSetListener, year, month, day)
    }
}