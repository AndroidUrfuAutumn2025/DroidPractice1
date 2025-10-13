package ru.urfu.droidpractice1

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

private const val TAG = "SecondActivity"
class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var readSwitch: Switch
    private lateinit var toolbar: Toolbar
    private lateinit var backButton: Button
    private var isRead: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate")

        readSwitch = binding.readSwitch
        toolbar = binding.toolbar
        backButton = binding.backButton

        isRead = savedInstanceState?.getBoolean(KEY_READ_STATUS)
            ?: intent.getBooleanExtra(EXTRA_READ_STATUS, false)

        readSwitch.isChecked = isRead

        toolbar.setNavigationOnClickListener {
            returnWithResult()
        }

        val imageUrl = "https://www.susu.ru/sites/default/files/styles/wide_news_image/public/field/image/1_271.jpg?itok=OBFJi5V-"
        binding.articleImageView.load(imageUrl) {
            crossfade(true)
        }

        readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
        }

        backButton.setOnClickListener {
            returnWithResult()
        }

        setupBackPressHandler()
    }

    private fun setupBackPressHandler() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returnWithResult()
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun returnWithResult() {
        val resultIntent = Intent().apply {
            putExtra(MainActivity.EXTRA_IS_READ, isRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_READ_STATUS, isRead)
    }

    companion object {
        private const val KEY_READ_STATUS = "read_status"
        const val EXTRA_READ_STATUS = "extra_read_status"
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}