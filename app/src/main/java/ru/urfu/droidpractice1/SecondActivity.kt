package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding

    private lateinit var readSwitch: Switch
    private var isRead: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Lifecycle", "SecondActivity onCreate")

        binding.toolbar.setNavigationOnClickListener {
            returnWithResult()
        }

        val imageUrl =
            "https://images.unsplash.com/photo-1574158622682-e40e69881006?auto=format&fit=crop&w=800&q=80"

        binding.articleImageView.load(imageUrl) {
            crossfade(true)
        }

        readSwitch = findViewById(R.id.readSwitch)

        isRead = savedInstanceState?.getBoolean(KEY_READ_STATUS)
            ?: intent.getBooleanExtra(EXTRA_READ_STATUS, false)

        readSwitch.isChecked = isRead

        readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
        }

        setupViews()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returnWithResult()
            }
        })
    }

    private fun returnWithResult() {
        val resultIntent = Intent().apply {
            putExtra(MainActivity.EXTRA_IS_READ, isRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun setupViews() {
        val subtitleTextView: TextView = findViewById(R.id.subtitleTextView)
        val contentTextView: TextView = findViewById(R.id.contentTextView)
        val quoteTextView: TextView = findViewById(R.id.quoteTextView)

        subtitleTextView.text = getString(R.string.second_article_subtitle)
        contentTextView.text = getString(R.string.second_article_content)
        quoteTextView.text = getString(R.string.second_article_quote)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_READ_STATUS, isRead)
    }

    companion object {
        private const val KEY_READ_STATUS = "read_status"
        const val EXTRA_READ_STATUS = "extra_read_status"
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "SecondActivity onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "SecondActivity onResume") }
    override fun onPause() { super.onPause(); Log.d("Lifecycle", "SecondActivity onPause") }
    override fun onStop() { super.onStop(); Log.d("Lifecycle", "SecondActivity onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("Lifecycle", "SecondActivity onDestroy") }
}
