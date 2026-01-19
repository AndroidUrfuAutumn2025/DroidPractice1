package ru.urfu.droidpractice1

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

private const val TAG = "SecondActivity_Lifecycle"
class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object{
        var isArticleRead = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.readSwitch.isChecked = isArticleRead
        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
        }

        binding.toolbar.setNavigationOnClickListener {
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

