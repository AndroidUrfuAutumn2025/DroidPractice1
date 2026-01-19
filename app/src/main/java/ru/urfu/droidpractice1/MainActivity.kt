package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class UserVoteState {
    LIKED, DISLIKED, NONE
}

class MainActivity : ComponentActivity() {

    private val TAG_LIFECYCLE = "MainActivityLifecycle"

    private val articleTitle = "Первая статья: Чудеса Compose"
    private val articleContent = """
        Jetpack Compose — это современный набор инструментов для создания нативного пользовательского интерфейса Android.
        Он упрощает и ускоряет разработку UI на Kotlin благодаря декларативному подходу.
        Вы описываете, как должен выглядеть ваш UI в определенном состоянии, а Compose сам заботится об его обновлении.

        В этой статье мы видим пример использования различных текстовых стилей, загрузки изображения из сети
        и интерактивных элементов, таких как кнопки лайков и "Поделиться".
        Состояние счетчиков лайков сохраняется даже при повороте экрана!
    """.trimIndent()
    private val articleImageUrl = "https://repository-images.githubusercontent.com/298064545/7c4d6e00-fe41-11ea-9c4f-6c3b47a99bbd"

    private var secondArticleReadStatus by mutableStateOf("Статус второй статьи: Неизвестно")

    private val startSecondActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val isRead = data?.getBooleanExtra(SecondActivity.EXTRA_IS_READ_RESULT, false) ?: false
                secondArticleReadStatus = if (isRead) {
                    "Статус второй статьи: Прочитана"
                } else {
                    "Статус второй статьи: Не прочитана"
                }
                Log.i("MainActivityResults", "Результат из SecondActivity: isRead = $isRead")
            } else {
                Log.i("MainActivityResults", "Результат из SecondActivity: отменено или ошибка (код: ${result.resultCode})")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_LIFECYCLE, "onCreate")

        if (savedInstanceState != null) {
            secondArticleReadStatus = savedInstanceState.getString("secondArticleStatus", secondArticleReadStatus)
        }


        setContent {
            MaterialTheme {
                var likesCount by rememberSaveable { mutableIntStateOf(10) }
                var dislikesCount by rememberSaveable { mutableIntStateOf(2) }
                var userVote by rememberSaveable { mutableStateOf(UserVoteState.NONE) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Article1Composable(
                        modifier = Modifier.weight(1f),
                        title = articleTitle,
                        text = articleContent,
                        imageUrl = articleImageUrl,
                        likes = likesCount,
                        dislikes = dislikesCount,
                        userVoteState = userVote,
                        onLikeClicked = {
                            when (userVote) {
                                UserVoteState.LIKED -> { likesCount--; userVote = UserVoteState.NONE }
                                UserVoteState.DISLIKED -> { likesCount++; dislikesCount--; userVote = UserVoteState.LIKED }
                                UserVoteState.NONE -> { likesCount++; userVote = UserVoteState.LIKED }
                            }
                            Log.i("MainActivityActions", "Лайк! Всего: $likesCount, Дизлайки: $dislikesCount, Состояние: $userVote")
                        },
                        onDislikeClicked = {
                            when (userVote) {
                                UserVoteState.DISLIKED -> { dislikesCount--; userVote = UserVoteState.NONE }
                                UserVoteState.LIKED -> { dislikesCount++; likesCount--; userVote = UserVoteState.DISLIKED }
                                UserVoteState.NONE -> { dislikesCount++; userVote = UserVoteState.DISLIKED }
                            }
                            Log.i("MainActivityActions", "Дизлайк! Всего: $likesCount, Дизлайки: $dislikesCount, Состояние: $userVote")
                        },
                        onShareClicked = {
                            shareArticle(articleTitle, articleContent)
                        },
                        onNavigateToNextArticleClicked = {
                            navigateToArticle2()
                        }
                    )

                    Text(
                        text = secondArticleReadStatus,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }

    private fun navigateToArticle2() {
        Log.i("MainActivityActions", "Запрос на переход ко второй статье")
        val intent = Intent(this, SecondActivity::class.java).apply {
        }
        startSecondActivityForResult.launch(intent)
    }

    private fun shareArticle(title: String, content: String) {
        val shareText = "$title\n\n$content"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        if (sendIntent.resolveActivity(packageManager) != null) {
            val shareIntent = Intent.createChooser(sendIntent, "Поделиться статьей через...")
            startActivity(shareIntent)
        } else {
            Log.w("MainActivityActions", "Нет приложений для обработки действия 'Поделиться'")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("secondArticleStatus", secondArticleReadStatus)
        Log.d(TAG_LIFECYCLE, "onSaveInstanceState: Saved secondArticleStatus = $secondArticleReadStatus")
    }

    override fun onStart() { super.onStart(); Log.d(TAG_LIFECYCLE, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG_LIFECYCLE, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG_LIFECYCLE, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG_LIFECYCLE, "onStop") }
    override fun onRestart() { super.onRestart(); Log.d(TAG_LIFECYCLE, "onRestart") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG_LIFECYCLE, "onDestroy") }
}




    