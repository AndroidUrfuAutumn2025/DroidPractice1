package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import ru.urfu.droidpractice1.content.MainActivityScreen
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

class MainActivity : ComponentActivity() {

    private lateinit var secondArticleLauncher: ActivityResultLauncher<Intent>
    private var isSecondArticleReadState = mutableStateOf(false)
    private var likesCountState = mutableStateOf(0)
    private var dislikesCountState = mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "MainActivity onCreate")

        if (savedInstanceState != null) {
            isSecondArticleReadState.value =
                savedInstanceState.getBoolean(KEY_SECOND_ARTICLE_READ, false)
            likesCountState.value = savedInstanceState.getInt(KEY_LIKES_COUNT, 0)
            dislikesCountState.value = savedInstanceState.getInt(KEY_DISLIKES_COUNT, 0)
        }

        secondArticleLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val wasRead = result.data?.getBooleanExtra(EXTRA_IS_READ, false) ?: false
                isSecondArticleReadState.value = wasRead
            }
        }

        setContent {
            DroidPractice1Theme {
                MainActivityScreen(
                    isSecondArticleRead = isSecondArticleReadState.value,
                    likesCount = likesCountState.value,
                    dislikesCount = dislikesCountState.value,
                    onLike = { likesCountState.value++ },
                    onDislike = { dislikesCountState.value++ },
                    onShare = { shareArticle() },
                    onNavigateToSecondArticle = {
                        val intent = Intent(this, SecondActivity::class.java).apply {
                            putExtra(
                                SecondActivity.EXTRA_READ_STATUS,
                                isSecondArticleReadState.value
                            )
                        }
                        secondArticleLauncher.launch(intent)
                    }
                )
            }
        }
    }

    private fun shareArticle() {
        val shareText = """
            📚 Магический мир Гарри Поттера 🪄
            
            Откройте для себя удивительный мир Хогвартса, магии и приключений!
            Прочитайте о факультетах, заклинаниях и волшебных существах.
            
            Присоединяйтесь к магическому сообществу! ✨
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться магией"))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SECOND_ARTICLE_READ, isSecondArticleReadState.value)
        outState.putInt(KEY_LIKES_COUNT, likesCountState.value)
        outState.putInt(KEY_DISLIKES_COUNT, dislikesCountState.value)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "MainActivity onDestroy")
    }

    companion object {
        const val REQUEST_CODE_SECOND_ACTIVITY = 1001
        const val EXTRA_IS_READ = "is_read"
        private const val KEY_SECOND_ARTICLE_READ = "second_article_read"
        private const val KEY_LIKES_COUNT = "likes_count"
        private const val KEY_DISLIKES_COUNT = "dislikes_count"
    }
}