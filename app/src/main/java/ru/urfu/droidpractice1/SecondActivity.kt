package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isRead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SecondActivity", "onCreate")
        
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Получаем статус прочтения из Intent
        isRead = intent.getBooleanExtra("is_read", false)
        binding.switchRead.isChecked = isRead
        Log.d("SecondActivity", "Initial read status: $isRead")

        binding.toolbar.setNavigationOnClickListener { 
            finishWithResult()
        }
        
        // Обработчик переключателя "Прочитано"
        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
            Log.d("SecondActivity", "Read status changed: $isChecked")
        }
        
        // Загружаем изображение
        Glide.with(this)
            .load("https://i.imgur.com/y1bW9HE.jpg")
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(binding.articleImage)
    }
    
    override fun onStart() {
        super.onStart()
        Log.d("SecondActivity", "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d("SecondActivity", "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d("SecondActivity", "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d("SecondActivity", "onStop")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d("SecondActivity", "onDestroy")
    }
    
    private fun finishWithResult() {
        Log.d("SecondActivity", "finishWithResult - isRead: $isRead")
        val resultIntent = Intent().apply {
            putExtra("is_read", isRead)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
    
    override fun onBackPressed() {
        Log.d("SecondActivity", "onBackPressed")
        finishWithResult()
    }
}