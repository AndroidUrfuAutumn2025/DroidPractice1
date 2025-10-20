package ru.urfu.droidpractice1

import android.app.Activity
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

        val wasRead = savedInstanceState?.getBoolean(KEY_READ_STATE) ?: false

        binding.switchRead.isChecked = wasRead

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            setResult(Activity.RESULT_OK, intent.putExtra(KEY_READ_STATE, isChecked))
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_READ_STATE, binding.switchRead.isChecked)
    }
}