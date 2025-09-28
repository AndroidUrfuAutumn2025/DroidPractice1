package ru.urfu.droidpractice1

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object {
        private const val TAG = "SecondActivity_Lifecycle"
        var isArticle2Read = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //переворот экрана
        //requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Log.d(TAG, "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.switch1.isChecked = isArticle2Read
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            isArticle2Read = isChecked
            Log.d(TAG, "Switch changed to: $isChecked")
        }

        binding.toolbar.setNavigationOnClickListener {
            Log.d(TAG, "Back button clicked")
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
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