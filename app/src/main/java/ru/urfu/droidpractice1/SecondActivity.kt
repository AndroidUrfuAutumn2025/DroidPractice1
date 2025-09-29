package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

import android.widget.TextView
import android.widget.ImageView
import android.widget.Switch
import android.content.Context
import android.content.Intent
import android.util.Log

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val PREFS_NAME = "Статьи"
    private val KEY_READ = "статья2_прочитана"

    private var switchState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        switchState = prefs.getBoolean(KEY_READ, false)
        binding.switchBackButton.isChecked = switchState
        binding.switchBackButton.setOnCheckedChangeListener { _, isChecked ->
            switchState = isChecked
            prefs.edit().putBoolean(KEY_READ, isChecked).apply()
        }

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val mainTextView = findViewById<TextView>(R.id.mainTextView)
        val imageView = findViewById<ImageView>(R.id.articleImageView)


        titleTextView.text =
            "Начало прямой видеотрансляции мужской групповой гонки U23 на ЧМ по велоспорту"
        mainTextView.text =
            "26 сентября в 12:50 мск на странице Okko на «Чемпионате» начнётся прямая трансляция мужской групповой гонки U23 на чемпионате мира по велоспорту на шоссе 2025 года. Она пройдёт в Кигали (Руанда). Протяжённость маршрута составит 267,5 км."

        imageView.setImageResource(R.drawable.image1)


    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("switch_state_result", switchState)
        setResult(RESULT_OK, resultIntent)
        super.onBackPressed()
    }
}



