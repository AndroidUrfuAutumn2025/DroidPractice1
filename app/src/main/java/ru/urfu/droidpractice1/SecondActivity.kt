package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var likes = 0
    private var dislikes = 0
    private var isRead = false

    private val LIKES_KEY = "STATE_LIKES"
    private val DISLIKES_KEY = "STATE_DISLIKES"
    private val READ_STATE_KEY = "STATE_READ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        if (savedInstanceState != null) {
            // Если Activity пересоздается (поворот экрана), восстанавливаем все из бандла
            likes = savedInstanceState.getInt(LIKES_KEY, 0)
            dislikes = savedInstanceState.getInt(DISLIKES_KEY, 0)
            isRead = savedInstanceState.getBoolean(READ_STATE_KEY, false)
        } else {
            // Если Activity создается впервые, берем начальные значения из Intent
            likes = intent.getIntExtra("likes", 0)
            dislikes = intent.getIntExtra("dislikes", 0)
            isRead = intent.getBooleanExtra("is_read", false)
        }

        updateUi()
        setupClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.second_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareArticle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareArticle() {
        val articleTitle = binding.textView.text.toString()
        val articleBody = binding.textView2.text.toString()
        val fullArticleText = "$articleTitle\n\n$articleBody"

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, fullArticleText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun updateUi() {
        binding.likeCounter.text = likes.toString()
        binding.dislikeCounter.text = dislikes.toString()
        binding.readSwitch.isChecked = isRead
    }

    private fun setupClickListeners() {
        binding.likeButton.setOnClickListener {
            likes++
            binding.likeCounter.text = likes.toString()
        }

        binding.dislikeButton.setOnClickListener {
            dislikes++
            binding.dislikeCounter.text = dislikes.toString()
        }

        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LIKES_KEY, likes)
        outState.putInt(DISLIKES_KEY, dislikes)
        outState.putBoolean(READ_STATE_KEY, isRead)
    }

    override fun finish() {
        val resultIntent = Intent()
        // Возвращаем ВСЕ актуальные данные в MainActivity
        resultIntent.putExtra("likes", likes)
        resultIntent.putExtra("dislikes", dislikes)
        resultIntent.putExtra("is_read", isRead)
        setResult(Activity.RESULT_OK, resultIntent)
        super.finish()
    }
}