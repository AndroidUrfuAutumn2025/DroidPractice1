package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme


class MainActivity : ComponentActivity() {
    private var likeCount by mutableIntStateOf(0)
    private var dislikeCount by mutableIntStateOf(0)
    private var isSecondArticleRead by mutableStateOf(false)

    companion object {
        private const val SHARED_PREFS_NAME = "article_prefs"
        private const val KEY_LIKES = "likes"
        private const val KEY_DISLIKES = "dislikes"
        private const val KEY_SECOND_READ = "second_read"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "MainActivity onCreate")

        val prefs = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        likeCount = prefs.getInt(KEY_LIKES, 0)
        dislikeCount = prefs.getInt(KEY_DISLIKES, 0)
        isSecondArticleRead = prefs.getBoolean(KEY_SECOND_READ, false)

        setContent {
            DroidPractice1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArticleScreen(
                        likeCount = likeCount,
                        dislikeCount = dislikeCount,
                        isSecondArticleRead = isSecondArticleRead,
                        onLikeClick = {
                            likeCount++
                            saveState()
                        },
                        onDislikeClick = {
                            dislikeCount++
                            saveState()
                        },
                        onShareClick = { shareArticle() },
                        onSecondArticleClick = {
                            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                        }
                    )
                }
            }
        }
    }

    private fun saveState() {
        getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).edit().apply {
            putInt(KEY_LIKES, likeCount)
            putInt(KEY_DISLIKES, dislikeCount)
            putBoolean(KEY_SECOND_READ, isSecondArticleRead)
            apply()
        }
    }

    private fun shareArticle() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Посмотрите эту интересную статью: ${getArticleText()}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
    }

    private fun getArticleText(): String {
        return "Искусственный интеллект в современном мире: перспективы и вызовы. " +
                "Технологии машинного обучения меняют нашу жизнь каждый день."
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "MainActivity onResume")

        isSecondArticleRead = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
            .getBoolean(KEY_SECOND_READ, false)
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "MainActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "MainActivity onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "MainActivity onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Lifecycle", "MainActivity onRestart")
    }
}

@Composable
fun ArticleScreen(
    likeCount: Int,
    dislikeCount: Int,
    isSecondArticleRead: Boolean,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onShareClick: () -> Unit,
    onSecondArticleClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Заголовок
        Text(
            text = "Искусственный интеллект в современном мире",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Подзаголовок
        Text(
            text = "Перспективы и вызовы технологий будущего",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Изображение
        @OptIn(ExperimentalGlideComposeApi::class)
        GlideImage(
            model = "https://staticg.sportskeeda.com/editor/2023/09/4d675-16944207483457-1920.jpg",
            contentDescription = "Изображение через Glide",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            it.centerCrop()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Основной текст
        Text(
            text = "Искусственный интеллект (ИИ) становится неотъемлемой частью нашей жизни. " +
                    "От голосовых помощников до систем рекомендаций - технологии машинного обучения " +
                    "проникают во все сферы человеческой деятельности.\n\n" +
                    "Основные направления развития ИИ включают:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Маркированный список
        Text(
            text = "• Машинное обучение и глубокие нейросети\n" +
                    "• Обработка естественного языка\n" +
                    "• Компьютерное зрение\n" +
                    "• Робототехника и автономные системы",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Кнопка поделиться
        Button(
            onClick = onShareClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Share, contentDescription = "Поделиться")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Поделиться статьей")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Счетчик лайков/дизлайков
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LikeDislikeCounter(
                count = likeCount,
                icon = Icons.Default.Favorite,
                onClick = onLikeClick
            )

            LikeDislikeCounter(
                count = dislikeCount,
                icon = Icons.Default.FavoriteBorder,
                onClick = onDislikeClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Статус второй статьи
        Text(
            text = if (isSecondArticleRead) {
                "Вторая статья прочитана ✓"
            } else {
                "Вторая статья еще не прочитана"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSecondArticleRead) Color.Green else Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Кнопка перехода ко второй статье
        Button(
            onClick = onSecondArticleClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Читать вторую статью")
        }
    }
}

@Composable
fun LikeDislikeCounter(
    count: Int,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = null)
        }
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}