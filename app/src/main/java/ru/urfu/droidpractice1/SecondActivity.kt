package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isArticleRead: Boolean = false

    companion object {
        const val EXTRA_ARTICLE_READ = "article_read"
        const val RESULT_ARTICLE_READ = 1001
        private const val TAG = "SecondActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Восстановление состояния
        isArticleRead = savedInstanceState?.getBoolean("isArticleRead", false) ?: false
        updateReadStatus()

        // Настройка ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Искусственный интеллект"

        // Обработчик переключателя
        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
            updateReadStatus()

            // Отправляем результат обратно в MainActivity
            val resultIntent = Intent().apply {
                putExtra(EXTRA_ARTICLE_READ, isArticleRead)
            }
            setResult(RESULT_ARTICLE_READ, resultIntent)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putBoolean("isArticleRead", isArticleRead)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp - возврат в MainActivity")
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun updateReadStatus() {
        binding.readSwitch.isChecked = isArticleRead
        if (isArticleRead) {
            binding.readStatus.text = "Прочитано"
            binding.readStatus.setTextColor(getColor(R.color.green))
        } else {
            binding.readStatus.text = "Не прочитано"
            binding.readStatus.setTextColor(getColor(R.color.red))
        }
    }
}