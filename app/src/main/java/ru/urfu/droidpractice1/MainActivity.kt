package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    
    companion object {
        private const val TAG = "MainActivity"
    }
    
    private var isArticleRead by mutableStateOf(false)
    
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            isArticleRead = result.data?.getBooleanExtra(SecondActivity.EXTRA_ARTICLE_READ, false) ?: false
            Log.d(TAG, "Article read status received: $isArticleRead")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContent {
            MainActivityScreen(
                isArticleRead = isArticleRead,
                onNavigateToSecond = { 
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra(SecondActivity.EXTRA_ARTICLE_READ, isArticleRead)
                    resultLauncher.launch(intent)
                }
            )
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
    
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}