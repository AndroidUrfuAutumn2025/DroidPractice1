package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

private const val KEY_READ_STATE = "read_state"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen() {
    val context = LocalContext.current

    var likeCount by rememberSaveable { mutableStateOf(0) }
    var dislikeCount by rememberSaveable { mutableStateOf(0) }
    var isSecondRead by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isSecondRead = context.getSharedPreferences("prefs", android.content.Context.MODE_PRIVATE)
            .getBoolean(KEY_READ_STATE, false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val read = result.data?.getBooleanExtra(KEY_READ_STATE, false) ?: false
            isSecondRead = read
        }
    }

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.article_title)) },
                    actions = {
                        IconButton(onClick = {
                            val share = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    context.getString(R.string.share_text)
                                )
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    share,
                                    context.getString(R.string.share_chooser_title)
                                )
                            )
                        }) {
                            Icon(
                                Icons.Filled.Share,
                                contentDescription = stringResource(R.string.share_desc)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(context, SecondActivity::class.java).apply {
                                putExtra(KEY_READ_STATE, isSecondRead)
                            }
                            launcher.launch(intent)
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            if (isSecondRead) stringResource(R.string.second_article_read)
                            else stringResource(R.string.open_second_article)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = "https://avatars.mds.yandex.net/i?id=17ef0365c84fd4d0f5c268cff4aa152d_l-5232242-images-thumbs&n=13",
                    contentDescription = stringResource(R.string.article_image_desc),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.article_date),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.article_title),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.article_category),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFAAAAAA)
                )

                Spacer(modifier = Modifier.height(12.dp))

                val updates = listOf(
                    stringResource(R.string.update_1),
                    stringResource(R.string.update_2),
                    stringResource(R.string.update_3),
                    stringResource(R.string.update_4)
                )

                updates.forEach { item ->
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("• ", style = MaterialTheme.typography.bodyMedium)
                        Text(item, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { likeCount++ }) {
                            Icon(
                                Icons.Filled.ThumbUp,
                                contentDescription = stringResource(R.string.like_desc)
                            )
                        }
                        Text(text = likeCount.toString(), color = Color.Red)
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { dislikeCount++ }) {
                            Icon(
                                Icons.Filled.ThumbDown,
                                contentDescription = stringResource(R.string.dislike_desc)
                            )
                        }
                        Text(text = dislikeCount.toString(), color = Color.Red)
                    }
                    Spacer(Modifier.weight(1f))
                }

            }
        }
    }
}
