package ru.urfu.droidpractice1

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import androidx.core.content.edit

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("article_prefs", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.articleImage1.load("https://cs18.pikabu.ru/s/2025/05/13/14/bvjbdw3c_s.jpg") {
            crossfade(true)
        }

        binding.articleImage2.load("https://i.pinimg.com/originals/39/1b/92/391b92e4592898ff613a205baf8572ef.jpg") {
            crossfade(true)
        }

        val isRead = prefs.getBoolean("isRead", false)
        binding.readSwitch.isChecked = isRead

        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit { putBoolean("isRead", isChecked) }

            val resultIntent = Intent().apply {
                putExtra("isRead", isChecked)
            }
            setResult(RESULT_OK, resultIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "${this.localClassName}: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "${this.localClassName}: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "${this.localClassName}: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "${this.localClassName}: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "${this.localClassName}: onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LIFECYCLE", "${this.localClassName}: onRestart")
    }

}