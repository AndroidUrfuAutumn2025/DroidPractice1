package ru.urfu.droidpractice1

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object {
        var isArticleRead = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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