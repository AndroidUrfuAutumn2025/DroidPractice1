package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    var likeCounter = mutableIntStateOf(0)
    var dislikeCounter = mutableIntStateOf(0)
    var isLikePressed = mutableStateOf(false)
    var isDislikePressed = mutableStateOf(false)

    var isArticleRead = false

    val secondActivityData = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                isArticleRead = data.getBooleanExtra("isRead", false)

                setContent {
                    MainActivityScreen(likeCounter, dislikeCounter, isLikePressed, isDislikePressed, isArticleRead,
                        { secondActivity() })
                }
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "OnCreate")
        savedInstanceState?.let {
            likeCounter.intValue = it.getInt("likeCounter", 0)
            dislikeCounter.intValue = it.getInt("likeCounter", 0)
            isLikePressed.value = it.getBoolean("isLikePressed", false)
            isDislikePressed.value = it.getBoolean("isDislikePressed", false)
            isArticleRead= it.getBoolean("isArticleRead", false)
        }
        setContent {
            MainActivityScreen(likeCounter, dislikeCounter, isLikePressed, isDislikePressed, isArticleRead, { secondActivity() })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity","likeCounter = ${likeCounter.intValue}, " +
                "dislikeCounter = ${dislikeCounter.intValue}," +
                "isLikePressed = ${isLikePressed.value}," +
                "isDislikePressed = ${isDislikePressed.value}," +
                "isArticleRead = $isArticleRead")
        outState.putInt("likeCounter", likeCounter.intValue)
        outState.putInt("dislikeCounter", dislikeCounter.intValue)
        outState.putBoolean("isLikePressed", isLikePressed.value)
        outState.putBoolean("isDislikePressed", isDislikePressed.value)
        outState.putBoolean("isArticleRead", isArticleRead)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MainActivity","likeCounter = ${likeCounter.intValue}, " +
                "dislikeCounter = ${dislikeCounter.intValue}," +
                "isLikePressed = ${isLikePressed.value}," +
                "isDislikePressed = ${isDislikePressed.value}," +
                "isArticleRead = $isArticleRead")
            likeCounter.intValue = savedInstanceState.getInt("likeCounter")
            dislikeCounter.intValue = savedInstanceState.getInt("dislikeCounter")
            isLikePressed.value = savedInstanceState.getBoolean("isLikePressed")
            isDislikePressed.value = savedInstanceState.getBoolean("isDislikePressed")
            isArticleRead= savedInstanceState.getBoolean("isArticleRead")
    }

    fun secondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("isArticleRead", isArticleRead)
        secondActivityData.launch(intent)
    }
}