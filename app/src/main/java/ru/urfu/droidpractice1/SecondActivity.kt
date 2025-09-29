package ru.urfu.droidpractice1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SecondActivity", "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val isReadSwitch = findViewById<Switch>(R.id.isReadSwitch)

        isReadSwitch.isChecked = intent.getBooleanExtra("isArticleRead", false)

        binding.toolbar.setNavigationOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("isRead", isReadSwitch.isChecked)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}