package ru.urfu.droidpractice1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import coil.load

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val titleView = findViewById<TextView>(R.id.articleTitle)
        val imageView = findViewById<ImageView>(R.id.articleImage)
        val textView = findViewById<TextView>(R.id.articleText)
        val backButton = findViewById<Button>(R.id.buttonBack)

        titleView.text = "Результаты первого сезона гонок RPT2025"
        textView.text = "Подробности и итоги первого сезона соревнований RPT2025: кто занял призовые места, статистика гонок и многое другое."

        val imageUrl = "https://f1-times.ru/wp-content/uploads/2023/01/imggpstartwif.jpg"
        imageView.load(imageUrl) {
        }

        backButton.setOnClickListener {
            val intent = intent.apply {
                putExtra("article_read", true)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}