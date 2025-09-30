package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    companion object {
        private const val TAG = "SecondActivity"
        const val EXTRA_ARTICLE_READ = "article_read"
    }

    private lateinit var binding: ActivitySecondBinding
    private var isArticleRead = false

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            isArticleRead = result.data?.getBooleanExtra(EXTRA_ARTICLE_READ, false) ?: false
            binding.readCheckbox.isChecked = isArticleRead
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        isArticleRead = intent.getBooleanExtra(EXTRA_ARTICLE_READ, false)
        Log.d(TAG, "Initial article read status: $isArticleRead")

        binding.toolbar.setNavigationOnClickListener { 
            finishWithResult()
        }

        Glide.with(this)
            .load("https://img.championat.com/s/1350x900/news/big/f/p/avariya-smita-v-gonke-nascar-cup-series-v-kanzase_1759148566826098227.jpg")
            .into(binding.articleImage)

        binding.readCheckbox.isChecked = isArticleRead

        binding.readCheckbox.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
            updateSwitchColors()
            Log.d(TAG, "Article read status changed: $isArticleRead")
        }
        
        updateSwitchColors()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    private fun updateSwitchColors() {
        if (isArticleRead) {
            binding.readCheckbox.thumbTintList = android.content.res.ColorStateList.valueOf(0xFF4CAF50.toInt())
            binding.readCheckbox.trackTintList = android.content.res.ColorStateList.valueOf(0xFF4CAF50.toInt())
        } else {
            binding.readCheckbox.thumbTintList = android.content.res.ColorStateList.valueOf(0xFF9E9E9E.toInt())
            binding.readCheckbox.trackTintList = android.content.res.ColorStateList.valueOf(0xFF9E9E9E.toInt())
        }
    }
    
    private fun finishWithResult() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_ARTICLE_READ, isArticleRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
    
    override fun onBackPressed() {
        finishWithResult()
    }
}