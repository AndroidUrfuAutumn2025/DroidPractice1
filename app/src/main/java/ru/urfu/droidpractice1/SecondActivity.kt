package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.content.edit
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Lifecycle", "SecondActivity onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.image.load("https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=400")

        val wasRead = intent.getBooleanExtra("read_state", false)
        binding.switchRead.isChecked = wasRead

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            val resultIntent = Intent().apply {
                putExtra("read_state", isChecked)
            }
            setResult(RESULT_OK, resultIntent)

            getSharedPreferences("app_prefs", MODE_PRIVATE).edit {
                putBoolean("read_state", isChecked)
            }
        }
    }

    override fun onStart() {
        Log.d("Lifecycle", "SecondActivity onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("Lifecycle", "SecondActivity onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("Lifecycle", "SecondActivity onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("Lifecycle", "SecondActivity onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "SecondActivity onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.d("Lifecycle", "SecondActivity onRestart")
        super.onRestart()
    }
}