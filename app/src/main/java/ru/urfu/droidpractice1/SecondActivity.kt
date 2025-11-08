package ru.urfu.droidpractice1

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.edit
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imageView = findViewById<ImageView>(R.id.article_image)
        val imageUrl = "https://cmsassets.rgpub.io/sanity/images/dsfx7636/news_live/79be60bb23b3da854acf80b2b519c93ad" +
                "9cd8588-915x515.jpg?auto=format&fit=fill&q=80&w=915"

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(imageView)

        val wasRead = intent.getBooleanExtra("read_state", false)
        binding.hasBeenReadCheckbox.isChecked = wasRead
        setResult(RESULT_OK, Intent().apply { putExtra("read_state", wasRead) })
        binding.hasBeenReadCheckbox.setOnCheckedChangeListener { _, isChecked ->
            val data = Intent().apply { putExtra("read_state", isChecked) }
            setResult(RESULT_OK, data)
            getSharedPreferences("prefs", MODE_PRIVATE).edit {
                putBoolean("read_state", isChecked)
            }
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    override fun onStart() {
        Log.d("Lifecycle", "SecondActivity onStart")
        super.onStart();
    }

    override fun onResume() {
        Log.d("Lifecycle", "SecondActivity onResume")
        super.onResume();
    }

    override fun onPause() {
        Log.d("Lifecycle", "SecondActivity onPause")
        super.onPause();
    }

    override fun onStop() {
        Log.d("Lifecycle", "SecondActivity onStop")
        super.onStop();
    }

    override fun onDestroy() {
        Log.d("Lifecycle", "SecondActivity onDestroy")
        super.onDestroy();
    }
}