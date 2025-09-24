@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen() {
    var likes by rememberSaveable { mutableStateOf(0) }
    var dislikes by rememberSaveable { mutableStateOf(0) }
    var isArticleRead by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val read = result.data?.getBooleanExtra("article_read", false) ?: false
            if (read) {
                isArticleRead = true
            }
        }
    }

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Скорее читай со мной!")
                                type = "text/plain"
                            }
                            val chooser = Intent.createChooser(shareIntent, "Поделиться через")
                            context.startActivity(chooser)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.little_description),
                    fontSize = 30.sp,
                    color = Color.Black,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = "https://origin.bk6bba-resources.com/ContentRed/Media/Articles_2024/Extra/TopEarners/sotsseti_Ronaldu-358.jpg",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.main_text),
                    fontSize = 20.sp,
                    color = Color.Black,
                    lineHeight = 36.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    launcher.launch(intent)
                }, modifier = Modifier.fillMaxWidth(), shape = RectangleShape ) {
                    Text(
                        text = if (isArticleRead)
                            "Следующая статья: Результаты первого сезона гонок RPT2025 (прочитано)"
                        else
                            "Следующая статья: Результаты первого сезона гонок RPT2025",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { likes++ }) {
                        Icon(
                            imageVector = Icons.Filled.ThumbUp,
                            contentDescription = "Лайк",
                            tint = Color.Green,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Text(text = likes.toString(), fontSize = 20.sp)

                    IconButton(onClick = { dislikes++ }) {
                        Icon(
                            imageVector = Icons.Filled.ThumbDown,
                            contentDescription = "Дизлайк",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Text(text = dislikes.toString(), fontSize = 20.sp)
                }
            }
        }
    }
}