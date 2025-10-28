package ru.urfu.droidpractice1

import androidx.activity.ComponentActivity
import android.os.Bundle
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import coil.load


class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var read = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        read = savedInstanceState?.getBoolean("read") ?: false
        binding.switchRead.isChecked = read

        binding.image.load("https://picsum.photos/400/300"){
            crossfade(true)
        }

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            read = isChecked
            val resultIntent = intent.apply {
                putExtra("read", read)
            }
            setResult(RESULT_OK, resultIntent)

        }
     }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("read", read)


    }
}