package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import coil.load

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_READ_STATUS = "read_status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SecondActivity", "onCreate")
        setContentView(R.layout.activity_second)

        val readSwitch = findViewById<Switch>(R.id.readSwitch)
        val backButton = findViewById<Button>(R.id.backButton)
        val articleImageView = findViewById<ImageView>(R.id.articleImageView)

        // Устанавливаем текущее состояние "Прочитано"
        val wasRead = intent.getBooleanExtra(EXTRA_READ_STATUS, false)
        readSwitch.isChecked = wasRead

        // Загружаем картинку через Coil
        val imageUrl =
            "https://img.championat.com/s/732x488/news/big/a/a/trener-spartaka-sakich-vyska.jpg"
        articleImageView.load(imageUrl) {
            crossfade(true)
            placeholder(R.color.purple_200)
            error(R.color.purple_700)
        }

        backButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_READ_STATUS, readSwitch.isChecked)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
