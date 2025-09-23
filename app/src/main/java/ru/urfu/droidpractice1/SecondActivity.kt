package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {
    private lateinit var readSwitch: Switch
    private lateinit var backButton: Button
    private lateinit var articleTitle: TextView
    private lateinit var articleContent: TextView
    private lateinit var articleImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "SecondActivity - onCreate")
        setContentView(R.layout.activity_second)

        // View'leri tanımla
        readSwitch = findViewById(R.id.readSwitch)
        backButton = findViewById(R.id.backButton)
        articleTitle = findViewById(R.id.articleTitle)
        articleContent = findViewById(R.id.articleContent)
        articleImage = findViewById(R.id.articleImage)

        // Intent'ten gelen verileri al
        val title = intent.getStringExtra("ARTICLE_TITLE") ?: "Fernando Costanza: 'Himki Maçında Eksik Olan Gol Duygusu'"
        val content = intent.getStringExtra("ARTICLE_CONTENT") ?: "Brezilyalı futbolcu 'Kanatlar Sovyetov' takımından Fernando Costanza, Rusya Premier Ligi'nin 10. haftasında Himki ile oynanan maçın berabere bitmesini yorumladı."

        // Makale verilerini göster
        articleTitle.text = title
        articleContent.text = content
        // Görsel zaten XML'de ayarlandı

        readSwitch.setOnCheckedChangeListener { _, isChecked ->
            val resultIntent = Intent()
            resultIntent.putExtra("IS_READ", isChecked)
            setResult(RESULT_OK, resultIntent)
            Log.d("SECOND_ACTIVITY", "Okundu durumu: $isChecked")
        }

        backButton.setOnClickListener {
            finishWithResult()
        }

        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishWithResult()
            }
        })
    }

    private fun finishWithResult() {
        val resultIntent = Intent()
        resultIntent.putExtra("IS_READ", readSwitch.isChecked)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "SecondActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "SecondActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "SecondActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "SecondActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "SecondActivity - onDestroy")
    }
}