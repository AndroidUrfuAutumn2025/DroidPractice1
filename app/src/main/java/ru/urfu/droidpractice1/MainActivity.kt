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
    
    private var likeCount by mutableStateOf(0)
    private var dislikeCount by mutableStateOf(0)
    private var isSecondArticleRead by mutableStateOf(false)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val isRead = result.data?.getBooleanExtra("is_read", false) ?: false
            isSecondArticleRead = isRead
            Log.d("MainActivity", "Second article read status updated: $isRead")
            Log.d("MainActivity", "Button text will change to: ${if (isRead) "Прочитано" else "Читать далее"}")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        
        savedInstanceState?.let {
            likeCount = it.getInt("like_count", 0)
            dislikeCount = it.getInt("dislike_count", 0)
        }
        
        setContent {
            MainActivityScreen(
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                isSecondArticleRead = isSecondArticleRead,
                onLikeClick = { likeCount++ },
                onDislikeClick = { dislikeCount++ },
                onShareClick = { shareArticle() },
                onReadMoreClick = { openSecondArticle() }
            )
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "onSaveInstanceState")
        outState.putInt("like_count", likeCount)
        outState.putInt("dislike_count", dislikeCount)
    }
    
    private fun shareArticle() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.first_article_content))
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_article)))
    }
    
    private fun openSecondArticle() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("is_read", isSecondArticleRead)
        startForResult.launch(intent)
    }
}