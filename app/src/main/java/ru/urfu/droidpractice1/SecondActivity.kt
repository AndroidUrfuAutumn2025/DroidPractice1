package ru.urfu.droidpractice1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import com.bumptech.glide.Glide

class SecondActivity : ComponentActivity() {

    companion object {
        const val EXTRA_READ = "extra_read"
        private const val PREFS = "second_article_prefs"
        private const val KEY_READ = "read"
    }

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle-Second", "onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        Glide.with(this)
            .load("https://vinascript.com/wp-content/uploads/2023/11/ANDROID-STUDIO.jpg")
            .centerCrop()
            .into(binding.image)

        val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val wasRead = prefs.getBoolean(KEY_READ, false)
        binding.switchRead.isChecked = wasRead

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(KEY_READ, isChecked).apply()
        }

        binding.btnFinish.setOnClickListener {
            val read = binding.switchRead.isChecked
            val data = Intent().apply { putExtra(EXTRA_READ, read) }
            setResult(RESULT_OK, data)
            finish()
        }
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle-Second", "onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle-Second", "onResume") }
    override fun onPause() { super.onPause(); Log.d("Lifecycle-Second", "onPause") }
    override fun onStop() { super.onStop(); Log.d("Lifecycle-Second", "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("Lifecycle-Second", "onDestroy") }
}
