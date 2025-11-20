package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    private val secondActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            isSecondArticleRead = result.data?.getBooleanExtra("isRead", false) ?: false
        }
    }

    companion object {
        var isSecondArticleRead by mutableStateOf(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContent {
            var likeCount by rememberSaveable { mutableIntStateOf(0) }
            var dislikeCount by rememberSaveable { mutableIntStateOf(0) }
            var interactionState by rememberSaveable { mutableStateOf("none") } // "liked", "disliked", "none"

            MainActivityScreen(
                onNavigateToSecondArticle = {
                    val intent = Intent(this, SecondActivity::class.java).apply {
                        putExtra("isRead", isSecondArticleRead)
                    }
                    secondActivityLauncher.launch(intent)
                },
                isSecondArticleRead = isSecondArticleRead,
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                isLiked = interactionState == "liked",
                isDisliked = interactionState == "disliked",
                onLikeClick = {
                    when (interactionState) {
                        "liked" -> {
                            likeCount--
                            interactionState = "none"
                        }

                        "disliked" -> {
                            dislikeCount--
                            likeCount++
                            interactionState = "liked"
                        }

                        else -> {
                            likeCount++
                            interactionState = "liked"
                        }
                    }
                },
                onDislikeClick = {
                    when (interactionState) {
                        "disliked" -> {
                            dislikeCount--
                            interactionState = "none"
                        }

                        "liked" -> {
                            likeCount--
                            dislikeCount++
                            interactionState = "disliked"
                        }

                        else -> {
                            dislikeCount++
                            interactionState = "disliked"
                        }
                    }
                },
                onShareClick = {
                    shareArticle()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
    }

    private fun shareArticle() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_SUBJECT,
                "«Кайрат» проиграл два матча в Лиге чемпионов с общим счётом 1:9"
            )
            putExtra(
                Intent.EXTRA_TEXT,
                "Казахстанский «Кайрат» проиграл два матча в общем этапе Лиги чемпионов сезона-2025/2026 с общим счётом 1:9. Сегодня, 30 сентября, клуб потерпел домашнее поражение от «Реала» со счётом 0:5. https://www.championat.com/"
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться статьей через..."))
    }
}