package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    companion object {
        private const val TAG = "SecondActivityLifecycle"
    }

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("article_prefs", MODE_PRIVATE)

        val savedState = prefs.getBoolean("is_read", false)

        binding.readSwitch.isChecked = savedState

        sendResult(savedState)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->

            prefs.edit()
                .putBoolean("is_read", isChecked)
                .apply()

            sendResult(isChecked)
        }
    }

    private fun sendResult(isRead: Boolean) {

        val resultIntent = Intent()

        resultIntent.putExtra("is_read", isRead)

        setResult(Activity.RESULT_OK, resultIntent)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}