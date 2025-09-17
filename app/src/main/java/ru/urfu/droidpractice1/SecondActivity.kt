package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import android.content.Intent
import android.util.Log
import android.widget.Toast
import coil.load
import androidx.core.content.edit

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SecondActivity onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val wasRead = intent.getBooleanExtra("read_state", false)
        binding.switchRead.isChecked = wasRead

        // Передаём текущее значение сразу, чтобы первая активити обновила UI даже без переключения
        setResult(RESULT_OK, Intent().apply { putExtra("read_state", wasRead) })

        binding.image.load("https://images.steamusercontent.com/ugc/1689399388028819966/E3A2BE80474B69447BD2E0D2BA6EC7D2AC6229DA/") {
            crossfade(true)
        }

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            val data = Intent().apply { putExtra("read_state", isChecked) }
            setResult(RESULT_OK, data)
            getSharedPreferences("prefs", MODE_PRIVATE).edit {
                putBoolean("read_state", isChecked)
            }
        }
    }

    override fun onStart() {
        Log.d("Lifecycle", "SecondActivity onStart")
        super.onStart();
    }

    override fun onResume() {
        Log.d("Lifecycle", "SecondActivity onResume")
        super.onResume();
    }

    override fun onPause() {
        Log.d("Lifecycle", "SecondActivity onPause")
        super.onPause();
    }

    override fun onStop() {
        Log.d("Lifecycle", "SecondActivity onStop")
        super.onStop();
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "SecondActivity onDestroy")
        super.onDestroy();
    }
}