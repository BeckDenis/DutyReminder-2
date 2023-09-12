package denis.beck.dutyreminder_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import denis.beck.dutyreminder_2.remindManager.RemindManager

class MainActivity : AppCompatActivity() {

    private lateinit var remindManager : RemindManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        remindManager = RemindManager(this)

        remindManager.setReminder()
    }

}