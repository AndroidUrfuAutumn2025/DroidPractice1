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
        var likeCount = 0
        var dislikeCount = 0
        val prefs = getSharedPreferences("article_prefs", MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.articleImage1.load("https://s.momenty.org/static/upload/pub/018/561/18561/239051_Ekaterinburgskiy_zoopark_Ekaterinburg_lenivets_kormezhka_kormlenie_980x0_4293.2862.0.0.jpg") {
            crossfade(true)
        }

        binding.articleImage2.load("https://s.momenty.org/static/upload/2022/11/15/be242c8ed4eff766549ec8cd1bec92c3_1200x0_596.763.0.226.jpg") {
            crossfade(true)
        }

        val isRead = prefs.getBoolean("article_read", false)
        binding.readSwitch.isChecked = isRead

        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit { putBoolean("article_read", isChecked) }

            val resultIntent = Intent().apply {
                putExtra("article_read", isChecked)
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