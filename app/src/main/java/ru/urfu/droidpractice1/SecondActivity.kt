package ru.urfu.droidpractice1

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.Toolbar
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SecondActivity", "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val readSwitch = findViewById<Switch>(R.id.switch1)

        readSwitch.isChecked = intent.getBooleanExtra("isRead", false)

        binding.toolbar.setNavigationOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("isRead", readSwitch.isChecked)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}