package ru.urfu.droidpractice1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isRead: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Lifecycle", "SecondActivity onCreate")

        // Получаем статус из Intent
        isRead = intent.getBooleanExtra(EXTRA_READ_STATUS, false)

        setupViews()
        setupClickListeners()
        loadImage()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            returnWithResult()
        }

        binding.readSwitch.isChecked = isRead
        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
            Log.d("SecondActivity", "Read status changed to: $isRead")
        }
    }

    private fun loadImage() {
        val imageUrl = "https://i.pinimg.com/originals/49/33/3c/49333c14d76e5c6ae692c28aded83717.png"
        binding.articleImageView.load(imageUrl) {
            crossfade(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.subtitleTextView.text = getString(R.string.subtitle)
        binding.contentTextView.text = getString(R.string.hogwarts_content)
        binding.quoteTextView.text = getString(R.string.hogwarts_quote)
    }

    private fun returnWithResult() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_IS_READ, isRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_READ_STATUS, isRead)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isRead = savedInstanceState.getBoolean(KEY_READ_STATUS, false)
        binding.readSwitch.isChecked = isRead
    }

    override fun onBackPressed() {
        returnWithResult()
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        Log.d(CLASS, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(CLASS, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(CLASS, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(CLASS, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(CLASS, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(CLASS, "onDestroy")
    }

    companion object {
        private const val KEY_READ_STATUS = "read_status"
        const val EXTRA_READ_STATUS = "extra_read_status"
        const val EXTRA_IS_READ = "is_read"
        private const val CLASS = "SecondActivity"
    }
}