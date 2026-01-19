package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import coil3.load
import coil3.request.crossfade
import coil3.size.Scale
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import androidx.core.content.edit

class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isArticleRead = false
    private val TAG = "SecondActivity_lifecycle"

    companion object {
        private const val PREFS_NAME = "article_prefs"
        private const val KEY_ARTICLE_READ = "article_read"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        isArticleRead = sharedPreferences.getBoolean(KEY_ARTICLE_READ, false)

        binding.toolbar.setNavigationOnClickListener {
            returnResult()
            onBackPressedDispatcher.onBackPressed()
        }

        setupSwitch()
        loadImageWithCoil()
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

    private fun loadImageWithCoil() {
        val imageUrl = "https://images.cybersport.ru/images/material-card/plain/73/73fd9eb7-0f4b-4dc1-ad52-29a5dc482691.jpg"

        binding.imageView.load(imageUrl) {
            crossfade(true)
            scale(Scale.FILL)
        }
    }

    private fun setupSwitch() {
        binding.switch2.isChecked = isArticleRead

        binding.switch2.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
            saveState(isChecked)
        }
    }

    private fun saveState(isRead: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_ARTICLE_READ, isRead)
        }
    }

    private fun returnResult() {
        val resultIntent = Intent().apply {
            putExtra("article_read", isArticleRead)
        }
        setResult(Activity.RESULT_OK, resultIntent)
    }
}