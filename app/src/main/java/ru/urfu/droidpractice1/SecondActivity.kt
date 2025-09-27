package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_NAME = "article_prefs"
        private const val KEY_SECOND_ARTICLE_READ = "second_article_read"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // инициализация SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // установка начального состояния Switch
        val isRead = sharedPreferences.getBoolean(KEY_SECOND_ARTICLE_READ, false)
        binding.switchRead.isChecked = isRead

        // обработчик нажатия на Switch
        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit()
                .putBoolean(KEY_SECOND_ARTICLE_READ, isChecked)
                .apply()
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}