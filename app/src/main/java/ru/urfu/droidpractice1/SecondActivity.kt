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
        binding.backButton.setOnClickListener {
            returnWithResult()
        }

        val imageUrl = "https://i.pinimg.com/originals/49/33/3c/49333c14d76e5c6ae692c28aded83717.png"
        binding.articleImageView.load(imageUrl) {
            crossfade(true)
        }

        isRead = savedInstanceState?.getBoolean(KEY_READ_STATUS)
            ?: intent.getBooleanExtra(EXTRA_READ_STATUS, false)
        binding.readSwitch.isChecked = isRead
        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
            Log.d("SecondActivity", "Read status changed to: $isRead")
        }

        setupViews()
    }

    private fun returnWithResult() {
        val resultIntent = Intent().apply {
            putExtra(MainActivity.EXTRA_IS_READ, isRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.subtitleTextView.text = "4 волшебных факультета Хогвартса"
        binding.contentTextView.text = """
            Хогвартс школа чародейства и волшебства состоит из четырех факультетов, каждый из которых основан одним из великих волшебников.

            Гриффиндор - основан Годриком Гриффиндором. Ценности: храбрость, благородство, честь и решительность.

            Слизерин - основан Салазаром Слизерином. Ценности: амбициозность, хитрость, находчивость.

            Когтевран - основан Кандидой Когтевран. Ценности: ум, мудрость, интеллект и творчество.

            Пуффендуй - основан Пенелопой Пуффендуй. Ценности: трудолюбие, верность, честность.
        """.trimIndent()
        binding.quoteTextView.text = "\"В Хогвартсе всегда примут тех, кого ждут.\" - Распределяющая Шляпа"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_READ_STATUS, isRead)
    }

    override fun onBackPressed() {
        returnWithResult()
        super.onBackPressed()
    }

    companion object {
        private const val KEY_READ_STATUS = "read_status"
        const val EXTRA_READ_STATUS = "extra_read_status"
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "SecondActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "SecondActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "SecondActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "SecondActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "SecondActivity onDestroy")
    }
}