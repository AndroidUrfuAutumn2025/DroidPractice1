package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import coil.load
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import android.content.Context
import android.util.Log

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val viewModel: SecondArticleViewModel by viewModels()
    private val tag = "Lifecycle-SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.cover.load("https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=1200")
        binding.inlineImage.load("https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?w=1200")

        binding.share.setOnClickListener {
            val shareText = "Baldur's Gate 3: новый стандарт RPG. " +
                    "Larian Studios установила новый стандарт для жанра с полностью интерактивным миром, " +
                    "глубоким сюжетом и тактическими сражениями. Лучшая RPG десятилетия!"
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            val chooser = Intent.createChooser(sendIntent, "Поделиться статьёй")
            startActivity(chooser)
        }

        updateCounters()

        binding.like.setOnClickListener {
            viewModel.like()
            updateCounters()
        }
        binding.dislike.setOnClickListener {
            viewModel.dislike()
            updateCounters()
        }

        binding.openFirst.setOnClickListener { finishWithResult() }

        // Restore read switch from preferences
        val prefs = getSharedPreferences("articles", Context.MODE_PRIVATE)
        val isRead = prefs.getBoolean("second_read", false)
        binding.readSwitch.isChecked = isRead

        binding.readSwitch.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean("second_read", checked).apply()
        }
    }

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

    private fun updateCounters() {
        binding.like.text = "Лайк: ${viewModel.likes}"
        binding.dislike.text = "Дизлайк: ${viewModel.dislikes}"
    }

    private fun finishWithResult() {
        val isRead = binding.readSwitch.isChecked
        setResult(RESULT_OK, Intent().putExtra("second_read", isRead))
        finish()
    }
}

class SecondArticleViewModel(private val state: SavedStateHandle) : ViewModel() {
    var likes: Int = state["likes"] ?: 0
        private set(value) {
            field = value
            state["likes"] = value
        }
    var dislikes: Int = state["dislikes"] ?: 0
        private set(value) {
            field = value
            state["dislikes"] = value
        }

    fun like() { likes = likes + 1 }
    fun dislike() { dislikes = dislikes + 1 }
}