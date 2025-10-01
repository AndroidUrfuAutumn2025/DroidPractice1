package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Состояние для первой статьи (MainActivity)
            var mainLikes by rememberSaveable { mutableStateOf(0) }
            var mainDislikes by rememberSaveable { mutableStateOf(0) }

            // Состояние для второй статьи (SecondActivity)
            var secondLikes by rememberSaveable { mutableStateOf(0) }
            var secondDislikes by rememberSaveable { mutableStateOf(0) }
            var isRead by rememberSaveable { mutableStateOf(false) }

            val secondActivityLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    secondLikes = data?.getIntExtra("likes", 0) ?: 0
                    secondDislikes = data?.getIntExtra("dislikes", 0) ?: 0
                    isRead = data?.getBooleanExtra("is_read", false) ?: false
                }
            }

            MainActivityScreen(
                likes = mainLikes,
                dislikes = mainDislikes,
                isSecondArticleRead = isRead,
                onLike = { mainLikes++ },
                onDislike = { mainDislikes++ },
                onOpenSecond = {
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("likes", secondLikes)
                    intent.putExtra("dislikes", secondDislikes)
                    intent.putExtra("is_read", isRead)
                    secondActivityLauncher.launch(intent)
                }
            )
        }
    }
}