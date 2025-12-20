package ru.urfu.droidpractice1.content

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.Color
@Composable
fun ArticleScreen(
    onNavigateToSecondArticle: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Счетчики лайков/дизлайков - сохраняются при перевороте экрана
    var likesCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikesCount by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Заголовок статьи (разный стиль)
        Text(
            text = "Jetpack Compose: Декларативный UI для Android",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Изображение (загружается через Coil)
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://www.nrhosting.com/wp-content/uploads/2021/01/Information-Technology.jpg"
            ),
            contentDescription = "Android разработка с Compose",
            modifier = Modifier
               .fillMaxWidth()
                .height(240.dp)
        )
     //   Box(
      //      modifier = Modifier
      //          .fillMaxWidth()
       //         .height(240.dp)
        //        .background(Color.Blue) // Просто синий прямоугольник
      //  ) {
        //    Text(
       //         text = "Здесь должна быть картинка",
       //         color = Color.White,
       //         modifier = Modifier.align(Alignment.Center)
       //     )
       // }
        Spacer(modifier = Modifier.height(24.dp))

        // 3. Тексты разных стилей (как в задании)
        Text(
            text = "Введение",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Jetpack Compose — это современный набор инструментов для построения пользовательских интерфейсов Android. Он позволяет создавать UI декларативным способом, описывая, как должен выглядеть интерфейс в различных состояниях.",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ключевые преимущества:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• Меньше кода\n• Интуитивный подход\n• Упрощенная навигация между состояниями\n• Совместимость с существующими View\n• Прямой доступ к Android API",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 4. Счетчик лайков/дизлайков (значения сохраняются)
        LikesDislikesCounter(
            likes = likesCount,
            dislikes = dislikesCount,
            onLikeClick = { likesCount++ },
            onDislikeClick = { dislikesCount++ }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 5. Кнопка "Поделиться" (использует Intent как в задании)
        Button(
            onClick = {
                shareArticle(
                    context = context,
                    title = "Jetpack Compose: Декларативный UI для Android",
                    content = "Узнайте о современном способе создания интерфейсов в Android..."
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text("Поделиться статьей")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 6. Кнопка перехода к второй статье
        Button(
            onClick = onNavigateToSecondArticle,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Читать вторую статью →")
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LikesDislikesCounter(
    likes: Int,
    dislikes: Int,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Оцените статью:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onLikeClick) {
                Text("👍 Лайков: $likes")
            }

            Button(onClick = onDislikeClick) {
                Text("👎 Дизлайков: $dislikes")
            }
        }
    }
}

// Функция "Поделиться" - использует Intent.ACTION_SEND (как в задании)
fun shareArticle(context: Context, title: String, content: String) {
    val shareText = """
        $title
        
        $content
        
        Читайте полную статью в приложении DroidPractice.
    """.trimIndent()

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
}