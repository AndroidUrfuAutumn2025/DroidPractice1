@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

private const val KEY_READ_STATE = "read_state"

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    var likeCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikeCount by rememberSaveable { mutableIntStateOf(0) }
    var isSecondRead by rememberSaveable { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val read = result.data?.getBooleanExtra(KEY_READ_STATE, false) ?: false
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            isSecondRead = read
        }
    }

    DroidPractice1Theme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                            title = { Text(text = stringResource(id = R.string.article_title_one)) },
                            actions = {
                                IconButton(onClick = {
                                    val share = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            context.getString(R.string.article_head_one)
                                        )
                                    }
                                    context.startActivity(Intent.createChooser(share, "Поделиться"))
                                }) {
                                    Icon(Icons.Filled.Share, contentDescription = "Поделиться")
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
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
                            ) {
                                Text(if (isSecondRead) "Вторая статья уже прочитана" else "Открыть вторую статью")
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

                        Text(
                            text = stringResource(id = R.string.article_head_one),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            fontSize = 28.sp
                        )
                        Spacer(Modifier.height(12.dp))

                        AsyncImage(
                            model = "https://awesomeworld.ru/wp-content/uploads/2015/04/Solnce_3.jpg",
                            contentDescription = "Солнце",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(12.dp))

                        Text(text = stringResource(id = R.string.article_one_paragraph_one))
                        Spacer(Modifier.height(8.dp))

                        Text(text = stringResource(id = R.string.article_one_paragraph_two))
                        Spacer(Modifier.height(8.dp))


                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { likeCount += 1 }) {
                                    Icon(Icons.Filled.ThumbUp, contentDescription = "Like")
                                }
                                Text(text = likeCount.toString(), color = Color.Green)
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { dislikeCount += 1 }) {
                                    Icon(
                                        imageVector = Icons.Filled.ThumbUp,
                                        contentDescription = "Dislike",
                                        modifier = Modifier.scale(scaleX = -1f, scaleY = -1f)
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


    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainActivityScreen()
    }
