@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.core.content.ContextCompat.startActivity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import ru.urfu.droidpractice1.SecondActivity



@Composable
fun MainActivityScreen() {
    DroidPractice1Theme {

        var likes by rememberSaveable { mutableStateOf(0) }
        var dislikes by rememberSaveable { mutableStateOf(0) }
        var isSecondRead by rememberSaveable { mutableStateOf(false) }
        var context = LocalContext.current


        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val read = result.data?.getBooleanExtra("read", false)
            if (read == true) {
                isSecondRead = true
            }
        }


        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    }
                )
            }) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://picsum.photos/400/300",
                    contentDescription = "Article image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Так, это у нас джетпак компоуз",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "А вот так",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Или курсив не знаю",
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(onClick = { likes++ }) {
                        Text(text = "👍 $likes")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { dislikes++ }) {
                        Text(text = "👎 $dislikes")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT,"Статья")
                    }
                    startActivity(context, Intent.createChooser(shareIntent, "Поделиться статьей"), null)
                }) {
                    Text("Поделиться")
                }

                Spacer(modifier = Modifier.height(16.dp))

                val context = LocalContext.current
                Button(onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    launcher.launch(intent)
                }) {
                    Text(if (isSecondRead) "Вторая статья прочитана" else "Читать вторую статью")
                }
            }
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}