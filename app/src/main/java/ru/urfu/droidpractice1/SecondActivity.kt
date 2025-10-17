package ru.urfu.droidpractice1

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isArticleRead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wasRead = intent.getBooleanExtra(MainActivity.KEY_READ_STATE, false)
        isArticleRead = wasRead
        binding.readSwitch.isChecked = wasRead

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupReadSwitch()
    }

    private fun setupReadSwitch() {
        binding.readSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            isArticleRead = isChecked
            setResult(RESULT_OK, Intent().apply {
                putExtra(MainActivity.KEY_READ_STATE, isChecked)
            })
        }
    }

    override fun onBackPressed() {
        val resultIntent = Intent().apply {
            putExtra(MainActivity.KEY_READ_STATE, isArticleRead)
        }
        setResult(RESULT_OK, resultIntent)
        super.onBackPressed()
    }
}