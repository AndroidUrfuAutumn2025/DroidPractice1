package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

private const val KEY_READ_STATE = "read_state"

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SecondActivity onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val wasRead = getSharedPreferences("prefs", MODE_PRIVATE).getBoolean(KEY_READ_STATE, false)
        binding.switchRead.isChecked = wasRead

        binding.image.load(
            "https://avatars.mds.yandex.net/i?id=3a6be3abd982e03024ccaf42728e63e0_l-10697157-images-thumbs&n=13"
        ) {
            crossfade(true)
        }

        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            val data = Intent().apply { putExtra(KEY_READ_STATE, isChecked) }
            setResult(RESULT_OK, data)

            getSharedPreferences("prefs", MODE_PRIVATE).edit {
                putBoolean(KEY_READ_STATE, isChecked)
            }

            Toast.makeText(
                this,
                if (isChecked) "Статья отмечена как прочитанная" else "Статья не прочитана",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
