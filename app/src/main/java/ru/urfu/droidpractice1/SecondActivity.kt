package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

private const val TAG_SECOND = "SecondActivityLifecycle"
private const val KEY_READ_STATE = "read_state"

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isRead: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_SECOND, "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isRead = intent.getBooleanExtra(KEY_READ_STATE, false)
        binding.readSwitch.isChecked = isRead

        binding.readSwitch.setOnCheckedChangeListener { _, checked ->
            isRead = checked
            sendResult()
        }

        binding.backButton.setOnClickListener {
            sendResult()
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_SECOND, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_SECOND, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG_SECOND, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_SECOND, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_SECOND, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG_SECOND, "onRestart")
    }

    private fun sendResult() {
        val resultIntent = Intent().apply {
            putExtra(KEY_READ_STATE, isRead)
        }
        setResult(Activity.RESULT_OK, resultIntent)
    }

    override fun onBackPressed() {
        sendResult()
        super.onBackPressed()
    }
}
