@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(activity: Activity) {
    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.article_title)) }
                )
            }
        ) { innerPadding ->

            var likes by rememberSaveable { mutableIntStateOf(0) }
            var dislikes by rememberSaveable { mutableIntStateOf(0) }
            var isRead by rememberSaveable { mutableStateOf(false) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val read = result.data?.getBooleanExtra("isRead", false) ?: false
                    isRead = read
                }
            }

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Какая-то очень интересная статья",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter("https://user-images.githubusercontent.com/131547083/234182208-0ebdd2ed-8ef8-4060-94e9-a0865cfa5696.jpg"),
                    contentDescription = "Article image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Серьёзно, я даже нормальный текст для фейковой статьи придумать не смог. Чёт устал я, да и фантазия закончилась. Вообще я хотел сделать гайд как стать фембоем, а потом подумал и решил: да ну его нафиг. Спать хочу(",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Автор статьи: неизвестен",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { likes++ }, modifier = Modifier.weight(1f)) { Text("👍 $likes") }
                    Button(onClick = { dislikes++ }, modifier = Modifier.weight(1f)) { Text("👎 $dislikes") }
                }

                Spacer(modifier = Modifier.height(4.dp))


                val context = LocalContext.current
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) { Button(onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Прочитай мою статью!")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }, modifier = Modifier.weight(1f)) {
                    Text("Поделиться")
                }
                    Button(onClick = {
                        val intent = Intent(activity, SecondActivity::class.java)
                        intent.putExtra("isRead", isRead)
                        launcher.launch(intent)
                    }, modifier = Modifier.weight(1f)) {
                        Text("Ко второй статье")
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))




                if (isRead) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("✅ Вторая статья прочитана", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}








@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen(Activity())
}

