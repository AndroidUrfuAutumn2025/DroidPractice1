@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    isArticleRead: Boolean = false,
    onNavigateToSecond: () -> Unit = {}
) {
    var likeCount by remember { mutableStateOf(0) }
    var dislikeCount by remember { mutableStateOf(0) }
    val context = LocalContext.current

    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    },
                    actions = {
                        val articleTitle = stringResource(id = R.string.article1_title)
                        val articleContent = stringResource(id = R.string.article1_content)
                        val articleParagraph1 = stringResource(id = R.string.article1_paragraph1)
                        val articleParagraph2 = stringResource(id = R.string.article1_paragraph2)
                        val articleParagraph3 = stringResource(id = R.string.article1_paragraph3)
                        val shareText = stringResource(id = R.string.share_text)
                        
                        IconButton(onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, 
                                    "$articleTitle\n\n" +
                                    "$articleContent\n\n" +
                                    "$articleParagraph1\n\n" +
                                    "$articleParagraph2\n\n" +
                                    "$articleParagraph3"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(shareIntent, shareText))
                        }) {
                            Icon(Icons.Default.Share, contentDescription = shareText)
                        }
                    }
                )
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.article1_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                AsyncImage(
                    model = "https://img.championat.com/s/1350x900/news/big/d/i/shtajner-perevod-antonelli-v-formulu-1-ne-vhodil-v-iznachalnye-plany-volfa_17591326101772516814.jpg",
                    contentDescription = "Гюнтер Штайнер",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(id = R.string.article1_content),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(id = R.string.article1_paragraph1),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(id = R.string.article1_paragraph2),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(id = R.string.article1_paragraph3),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { likeCount++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("$likeCount")
                    }
                    
                    Button(
                        onClick = { dislikeCount++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("$dislikeCount")
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToSecond() }
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isArticleRead) 
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        else 
                            MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Читать далее",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isArticleRead) 
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                else 
                                    MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = if (isArticleRead) 
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                else 
                                    MaterialTheme.colorScheme.primary
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = stringResource(id = R.string.article2_title),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isArticleRead) 
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            else 
                                MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = stringResource(id = R.string.article2_content),
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = if (isArticleRead) 
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            else 
                                MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        if (isArticleRead) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Прочитано",
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
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