package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.Switch
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.CompoundButton
import android.content.Intent

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val imageView: ImageView = binding.articleImage
        Glide.with(this)
            .load("https://img.championat.com/s/732x488/news/big/t/u/otchyot-nyukasl-barselona-1-2_17582317922140140871.jpg")
            .centerCrop()
            .into(imageView)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val readKey = "article_read"
        val readSwitch: Switch = binding.readSwitch
        readSwitch.isChecked = prefs.getBoolean(readKey, false)

        readSwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            prefs.edit().putBoolean(readKey, isChecked).apply()
            val resultIntent = Intent()
            resultIntent.putExtra(readKey, isChecked)
            setResult(RESULT_OK, resultIntent)
        }
    }
}