@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    vm: ArticleViewModel,
    onOpenSecond: () -> Unit,
    onShare: (String) -> Unit
) {
    DroidPractice1Theme {
        val likes by vm.likes.collectAsState()
        val dislikes by vm.dislikes.collectAsState()
        val liked by vm.liked.collectAsState()
        val disliked by vm.disliked.collectAsState()
        val secondRead by vm.secondRead.collectAsState()

        val articleTitle = stringResource(R.string.article_title)
        val articleBody = stringResource(R.string.article_body)
        val articleImage =
            "https://gbcdn.mrgcdn.ru/uploads/post/1116/og_cover_image/7be512347c8377d063f988039edc669f"

        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = articleTitle) })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = articleTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (secondRead) {
                    Text(
                        text = "✓ Вторая статья прочитана",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2E7D32)
                    )
                }

                Text(
                    text = "Лид статьи — краткое введение",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                AsyncImage(
                    model = articleImage,
                    contentDescription = "Логотип Kotlin",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = articleBody,
                    style = MaterialTheme.typography.bodyLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { vm.like() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (liked) Color.Green else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = "👍 $likes")
                        }
                        Button(
                            onClick = { vm.dislike() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (disliked) Color.Red else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = "👎 $dislikes")
                        }
                    }
                    Button(onClick = { onShare("$articleTitle\n\n$articleBody") }) {
                        Text(text = stringResource(R.string.share_article))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onOpenSecond,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Читать вторую статью")
                }
            }
        }
    }
}
