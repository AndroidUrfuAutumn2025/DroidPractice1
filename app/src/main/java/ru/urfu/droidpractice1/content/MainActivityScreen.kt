@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    onNavigateToSecondArticle: () -> Unit = {},
    isSecondArticleRead: Boolean = false
) {
    var likes by remember { mutableIntStateOf(0) }
    var dislikes by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.article_title))
                    },
                    actions = {
                        IconButton(onClick = {
                            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    android.content.Intent.EXTRA_TEXT,
                                    "Почему E = mc²: подробное объяснение с минимумом формул и максимумом смысла\n\n${getFirstArticleText()}"
                                )
                            }
                            context.startActivity(
                                android.content.Intent.createChooser(
                                    shareIntent,
                                    "Поделиться статьей"
                                )
                            )
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Поделиться")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Почему E = mc²: подробное объяснение с минимумом формул и максимумом смысла",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.einstein),
                        contentDescription = "Формула E=mc²",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Эту формулу связывают с Эйнштейном (хотя и другие физики и математики выходили на неё), с атомным оружием и угрозой человечеству, с истиной в чистом виде...",
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = getFirstArticleText(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Оцените статью",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { likes++ }) {
                                        Icon(
                                            Icons.Default.ThumbUp,
                                            contentDescription = "Нравится",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Text(text = "$likes")
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { dislikes++ }) {
                                        Icon(
                                            Icons.Default.ThumbUp,
                                            contentDescription = "Не нравится",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.rotate(180f)
                                        )
                                    }
                                    Text(text = "$dislikes")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onNavigateToSecondArticle,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Читать следующую статью")
                    }

                    if (isSecondArticleRead) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Вторая статья прочитана!",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun getFirstArticleText(): String {
    return """28 сентября празднуется 80-летие атомной промышленности. А ещё в 2025-м году исполняется 120 лет, как Эйнштейн вывел формулу эквивалентности массы и энергии.

Вопрос "Почему E = mc²" можно трактовать двояко:
• Как вывести это соотношение и из каких условий?
• Почему именно эта формула, почему вокруг неё такой хайп?

Мы постараемся ответить на оба вопроса и ещё затронем массу сопутствующих."""
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}