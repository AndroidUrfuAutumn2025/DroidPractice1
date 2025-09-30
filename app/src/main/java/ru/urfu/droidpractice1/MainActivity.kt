package ru.urfu.droidpractice1

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {

    companion object {
        const val KEY_READ_STATE = "is_second_article_read"
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isRead = result.data?.getBooleanExtra(KEY_READ_STATE, false) ?: false
        setContent {
            MainActivityContent(isSecondArticleRead = isRead)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            MainActivityContent()
        }
    }

    @Composable
    fun MainActivityContent(isSecondArticleRead: Boolean = false) {
        var isSecondRead by remember { mutableStateOf(isSecondArticleRead) }

        MainActivityScreen(
            onNavigateToSecondArticle = {
                val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                    putExtra(KEY_READ_STATE, isSecondRead)
                }
                launcher.launch(intent)
            },
            isSecondArticleRead = isSecondRead
        )
    }
}