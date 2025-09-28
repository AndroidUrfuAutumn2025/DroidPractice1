package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import coil.Coil
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    companion object {
        private const val SHARED_PREFS_NAME = "article_prefs"
        private const val KEY_SECOND_READ = "second_read"
    }

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SecondActivity onCreate")
        setContentView(R.layout.activity_second)

        val imageView: ImageView = findViewById(R.id.articleImage)

        val imageLoader = ImageLoader.Builder(this)
            .crossfade(true)
            .build()

        val request = ImageRequest.Builder(this)
            .data("https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=500")
            .target(imageView)
            .build()

        imageLoader.enqueue(request)

        val readSwitch: Switch = findViewById(R.id.readSwitch)
        val backButton: Button = findViewById(R.id.backButton)

        val prefs = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        readSwitch.isChecked = prefs.getBoolean(KEY_SECOND_READ, false)

        readSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveReadStatus(isChecked)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun saveReadStatus(isRead: Boolean) {
        getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).edit().apply {
            putBoolean(KEY_SECOND_READ, isRead)
            apply()
        }
    }

    @OptIn(UnstableApi::class)
    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "SecondActivity onResume")
    }

    @OptIn(UnstableApi::class)
    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "SecondActivity onPause")
    }

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "SecondActivity onDestroy")
    }

    @OptIn(UnstableApi::class)
    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "SecondActivity onStart")
    }

    @OptIn(UnstableApi::class)
    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "SecondActivity onStop")
    }

    @OptIn(UnstableApi::class)
    override fun onRestart() {
        super.onRestart()
        Log.d("Lifecycle", "SecondActivity onRestart")
    }
}