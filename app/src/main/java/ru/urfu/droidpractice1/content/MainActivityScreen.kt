@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.state.ArticleState
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainActivityScreen(articleState: ArticleState = ArticleState()) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val articleTitle = stringResource(R.string.main_article_title)
    var numberOfLikes by rememberSaveable { mutableIntStateOf(articleState.likes) }
    var numberOfDislikes by rememberSaveable { mutableIntStateOf(articleState.dislikes) }
    var isArticleRead by rememberSaveable { mutableStateOf(articleState.isRead) }

    val secondActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                isArticleRead = data.getBooleanExtra("IS_READ", false)
            }
        }
    }

    LaunchedEffect(numberOfLikes, numberOfDislikes) {
        articleState.likes = numberOfLikes
        articleState.dislikes = numberOfDislikes
        articleState.isRead = isArticleRead
    }

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.header)
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, articleTitle)
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Поделиться"))
                            }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_share),
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    stringResource(R.string.main_article_title),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { numberOfLikes++ }) {
                            Icon(Icons.Filled.FavoriteBorder, contentDescription = "Like")
                        }
                        if(numberOfLikes != 0) {
                            Text(
                                text = numberOfLikes.toString()
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { numberOfDislikes++ }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Dislike")
                        }
                        if(numberOfDislikes != 0) {
                            Text(
                                text = numberOfDislikes.toString()
                            )
                        }
                    }
                }

                GlideImage(
                    model = "https://img.championat.com/s/1350x900/news/big/y/k/otchyot-barselona-real-sosedad-2-1_1759085921554350046.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Text(
                    stringResource(R.string.main_article_paragraph1),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    stringResource(R.string.main_article_paragraph2),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    stringResource(R.string.main_article_paragraph3),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = {
                        val intent = Intent(context, SecondActivity::class.java).apply {
                            putExtra("LIKES_COUNT", numberOfLikes)
                            putExtra("DISLIKES_COUNT", numberOfDislikes)
                            putExtra("IS_READ", isArticleRead)
                        }
                        secondActivityLauncher.launch(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isArticleRead) Color.LightGray else Color.DarkGray
                    )
                ) {
                    Text(stringResource(R.string.second_article_title))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}