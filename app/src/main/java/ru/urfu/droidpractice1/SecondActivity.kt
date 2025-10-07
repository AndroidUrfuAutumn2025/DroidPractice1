package ru.urfu.droidpractice1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.internal.updateLiveLiteralValue
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    @OptIn(InternalComposeApi::class)
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Lifecycle", "${this.localClassName} - onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var isArticleRead = intent.getBooleanExtra("IS_READ", false)

        Glide.with(binding.image)
            .load("https://img.championat.com/s/1350x900/news/big/p/s/otchyot-spartak-pari-nn-3-0_17590848501963466578.jpg")
            .into(binding.image)

        binding.isReadSwitch.isChecked = isArticleRead

        binding.isReadSwitch.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
        }
        binding.goBackToolbar.setNavigationOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("IS_READ", isArticleRead)
            }
            setResult(RESULT_OK, resultIntent)
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onStart() {
        Log.d("Lifecycle", "${this.localClassName} - onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("Lifecycle", "${this.localClassName} - onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("Lifecycle", "${this.localClassName} - onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("Lifecycle", "${this.localClassName} - onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "${this.localClassName} - onDestroy")
        super.onDestroy()
    }
}