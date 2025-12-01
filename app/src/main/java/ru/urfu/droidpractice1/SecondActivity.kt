package ru.urfu.droidpractice1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("LIFECYCLE", "SecondActivity: onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        val imageUrl = "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?q=80&w=1000&auto=format&fit=crop"


        Glide.with(binding.root)
            .asBitmap()
            .load(imageUrl)
            .into(binding.photo)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.switchRead.isChecked = sharedPreferences.getBoolean("is_second_article_read", false)
        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("is_second_article_read", isChecked).apply()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "SecondActivity: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "SecondActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "SecondActivity: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "SecondActivity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "SecondActivity: onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LIFECYCLE", "SecondActivity: onRestart")
    }
}