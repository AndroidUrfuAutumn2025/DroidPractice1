package ru.urfu.droidpractice1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import coil.load

class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val tag = "SecondActivity"

    // Счётчики лайков (сохраняем в SharedPreferences)
    private var likesCount = 0
    private var dislikesCount = 0
    private lateinit var prefs: android.content.SharedPreferences

    // Для получения результата из MainActivity (если понадобится)
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Обработка результата, если MainActivity что-то вернёт
        Log.d(tag, "Получен результат от MainActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем SharedPreferences для сохранения счётчиков
        prefs = getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
        loadCounters()

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        // 1. Настраиваем Toolbar (кнопка "Назад")
        binding.toolbar.setNavigationOnClickListener {
            Log.d(tag, "Нажата кнопка 'Назад' в Toolbar")
            onBackPressedDispatcher.onBackPressed()
        }

        // 2. Загружаем изображение через Coil (другая картинка)
        binding.articleImageView.load("https://img.freepik.com/free-vector/gradient-ui-ux-background_23-2149052117.jpg?semt=ais_hybrid&w=740&q=80") {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.stat_notify_error)
        }

        // 3. Обновляем счётчики на кнопках
        updateCounterButtons()

        // 4. Восстанавливаем состояние переключателя "Прочитано"
        val isRead = prefs.getBoolean("second_article_read", false)
        binding.readSwitch.isChecked = isRead
    }

    private fun setupListeners() {
        // 1. Обработчик лайков
        binding.likeButton.setOnClickListener {
            likesCount++
            saveCounters()
            updateCounterButtons()
            showToast("Статья понравилась!")
        }

        // 2. Обработчик дизлайков
        binding.dislikeButton.setOnClickListener {
            dislikesCount++
            saveCounters()
            updateCounterButtons()
            showToast("Статья не понравилась :(")
        }

        // 3. Кнопка "Поделиться"
        binding.shareButton.setOnClickListener {
            shareArticle()
        }

        // 4. Переключатель "Прочитано"
        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(tag, "Переключатель 'Прочитано': $isChecked")
            prefs.edit().putBoolean("second_article_read", isChecked).apply()

            // Отправляем результат в MainActivity (для примера)
            val resultIntent = Intent().apply {
                putExtra("is_read", isChecked)
            }
            setResult(RESULT_OK, resultIntent)

            showToast(if (isChecked) "Статья отмечена как прочитанная" else "Статья не прочитана")
        }

        // 5. Кнопка возврата в MainActivity
        binding.backButton.setOnClickListener {
            Log.d(tag, "Нажата кнопка возврата")
            finish() // Закрываем SecondActivity, возвращаемся в MainActivity
        }
    }

    private fun updateCounterButtons() {
        binding.likeButton.text = "👍 Лайк: $likesCount"
        binding.dislikeButton.text = "👎 Дизлайк: $dislikesCount"
    }

    private fun saveCounters() {
        prefs.edit()
            .putInt("second_likes", likesCount)
            .putInt("second_dislikes", dislikesCount)
            .apply()
    }

    private fun loadCounters() {
        likesCount = prefs.getInt("second_likes", 0)
        dislikesCount = prefs.getInt("second_dislikes", 0)
    }

    private fun shareArticle() {
        val shareText = """
            Вторая статья: Android Views (XML)
            
            Традиционный подход к созданию интерфейсов в Android с помощью XML-разметки.
            Изучите основы работы с View и ViewGroup.
            
            Прочитано в приложении DroidPractice.
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Вторая статья: Android Views")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Остальные методы логирования жизненного цикла (оставь как были)
    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        Log.d(tag, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tag, "onStop")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy")
        super.onDestroy()
    }
}