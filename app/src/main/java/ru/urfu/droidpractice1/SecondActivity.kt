package ru.urfu.droidpractice1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.ComponentActivity
import coil.load

class SecondActivity : ComponentActivity() {

    companion object {
        const val ARTICLE_READ_STATUS = MainActivity.ARTICLE_READ_STATUS
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SecondActivity", "onCreate")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)

        val articleReadSwitch = findViewById<Switch>(R.id.articleReadSwitch)
        val articleImageView = findViewById<ImageView>(R.id.articleImageView)

        val wasRead = intent.getBooleanExtra(ARTICLE_READ_STATUS, false)
        articleReadSwitch.isChecked = wasRead

        val imageUrl =
            "https://i.playground.ru/p/tVPiP41Px341Q7YBaiQTVA.png.webp?760xauto"
        articleImageView.load(imageUrl) { crossfade(true) }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            val intent = Intent()
            intent.putExtra(ARTICLE_READ_STATUS, articleReadSwitch.isChecked)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onStart() {
        Log.d("SecondActivity", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("SecondActivity", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("SecondActivity", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("SecondActivity", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("SecondActivity", "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.d("SecondActivity", "onRestart")
        super.onRestart()
    }
}