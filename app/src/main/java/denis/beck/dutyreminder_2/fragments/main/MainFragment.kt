package denis.beck.dutyreminder_2.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.remindManager.RemindManager

class MainFragment : Fragment() {

    private lateinit var remindManager : RemindManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        remindManager = RemindManager(requireContext())
        remindManager.setReminder()
    }

}