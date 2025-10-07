package ru.urfu.droidpractice1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object {
        var isArticleRead = false
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Glide.with(binding.image)
            .load("https://img.championat.com/s/1350x900/news/big/p/s/otchyot-spartak-pari-nn-3-0_17590848501963466578.jpg")
            .into(binding.image)

        binding.isReadSwitch.isChecked = isArticleRead

        binding.isReadSwitch.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
        }
        binding.goBackToolbar.setNavigationOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}