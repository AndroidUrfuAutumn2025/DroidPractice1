@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    val cypherAbilities = stringResource(id = R.string.cypher_abilities)

    var likesCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikesCount by rememberSaveable { mutableIntStateOf(0) }
    var isArticleRead by rememberSaveable { mutableStateOf(false) }

    val secondActivityLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.let { data ->
                isArticleRead = data.getBooleanExtra("read_state", false)
            }
        }
    }

    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title),
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 2,
                            textAlign = TextAlign.Center
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { shareArticle(context, cypherAbilities) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Поделиться статьей",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                )
            }) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {

                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){

                    Spacer(Modifier.height(12.dp))
                    AsyncImage(
                        model = "https://cmsassets.rgpub.io/sanity/images/dsfx7636/news/4a648cdbcbbeef137050deefeaf6a1369c606666-616x822.png?auto=format&fit=fill&q=80&w=352",
                        contentDescription = "Cypher",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(horizontal = 16.dp),
                        alignment = Alignment.Center
                    )
                    Text(
                        text = stringResource(id = R.string.first_article_content_text1),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        ),
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = cypherAbilities,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .padding(top = 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { likesCount++ },
                            modifier = Modifier
                                .padding(start = 0.dp, end = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ThumbUp,
                                contentDescription = "Лайк",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                        }
                        Text(
                            text = likesCount.toString(),
                            modifier = Modifier.padding(horizontal = 3.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(
                            onClick = { dislikesCount++ },
                            modifier = Modifier
                                .padding(start = 0.dp, end = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ThumbUp,
                                contentDescription = "Дизлайк",
                                modifier = Modifier
                                    .size(24.dp)
                                    .rotate(180f),
                                tint = Color.Unspecified
                            )
                        }
                        Text(
                            text = dislikesCount.toString(),
                            modifier = Modifier.padding(horizontal = 3.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    Text(
                        text = "How should you place your traps →",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 6.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = if (isArticleRead) 0.1f else 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                            .clickable {
                                val intent = Intent(context, SecondActivity::class.java)
                                intent.putExtra("read_state", isArticleRead)
                                secondActivityLauncher.launch(intent)
                            },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (isArticleRead) Color.Gray else Color.Unspecified
                        )
                    )
                }
            }
        }
    }
}

private fun shareArticle(context: Context, sharedText : String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, sharedText)
        type = "text/plain"
    }

    val chooserIntent = Intent.createChooser(shareIntent, "Поделиться статьей")
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooserIntent)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}
