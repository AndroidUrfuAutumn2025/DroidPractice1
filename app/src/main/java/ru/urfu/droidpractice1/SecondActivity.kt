package ru.urfu.droidpractice1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isRead = false
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val TAG = "SecondActivity"
        const val PREF_NAME = "SecondActivityPrefs"
        const val KEY_IS_READ = "is_read"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        loadSavedState()
        setupViews()
        setupBackPressedHandler()
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

    private fun setupViews() {
        val switchRead: Switch = binding.switchRead
        val readStatus: TextView = binding.readStatus
        val backButton: Button = binding.backButton
        val backToolbar: Toolbar = binding.toolbar

        switchRead.isChecked = isRead
        readStatus.text = if (isRead) "Статья прочитана" else "Статья не прочитана"

        switchRead.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
            readStatus.text = if (isChecked) "Статья прочитана" else "Статья не прочитана"
            saveState()
        }

        backButton.setOnClickListener {
            returnResultAndFinish()
        }

        backToolbar.setOnClickListener {
            returnResultAndFinish()
        }
    }

    private fun setupBackPressedHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returnResultAndFinish()
            }
        })
    }

    private fun returnResultAndFinish() {
        val resultIntent = Intent().apply {
            putExtra("is_read", isRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun saveState() {
        sharedPreferences.edit().putBoolean(KEY_IS_READ, isRead).apply()
    }

    private fun loadSavedState() {
        isRead = sharedPreferences.getBoolean(KEY_IS_READ, false)
    }
}
