package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableIntStateOf
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    var likeCount = mutableIntStateOf(0)
    var dislikeCount = mutableIntStateOf(0)
    var isRead = false

    val secondActivityRes = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                isRead = data.getBooleanExtra("isRead", false)
                Log.d("MainActivity", "Вторая статья прочитана?: $isRead")

                setContent {
                    MainActivityScreen(likeCount, dislikeCount, {secondArticle()}, isRead)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        savedInstanceState?.let {
            likeCount.value = it.getInt("likeCount", 0)
            dislikeCount.value = it.getInt("dislikeCount", 0)
            isRead = it.getBoolean("isRead", false)
        }
        Log.d("MainActivity", "Likes: ${likeCount.value} Dislikes: ${dislikeCount.value} isRead: ${isRead}")
        setContent {
            MainActivityScreen(likeCount, dislikeCount, {secondArticle()}, isRead)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "Saved data.")
        outState.putInt("likeCount", likeCount.value)
        outState.putInt("dislikeCount", dislikeCount.value)
        outState.putBoolean("isRead", isRead)
    }

    fun secondArticle(){
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("isRead", isRead)
        secondActivityRes.launch(intent)
    }
}