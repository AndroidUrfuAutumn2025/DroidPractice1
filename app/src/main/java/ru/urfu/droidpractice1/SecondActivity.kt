package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Switch
import androidx.activity.ComponentActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object {
        const val KEY_READ_STATE = "read_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val wasRead = prefs.getBoolean(KEY_READ_STATE, false)

        binding.switchRead.isChecked = wasRead

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(KEY_READ_STATE, isChecked).apply()
            setResult(Activity.RESULT_OK, intent.putExtra(KEY_READ_STATE, isChecked))
        }
    }
}
