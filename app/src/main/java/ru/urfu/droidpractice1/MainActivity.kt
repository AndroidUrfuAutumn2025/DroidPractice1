package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "MainActivity - onCreate")
        setContent {
            DroidPractice1Theme {
                MainActivityScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "MainActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "MainActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "MainActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "MainActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "MainActivity - onDestroy")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen() {
    var likeCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikeCount by rememberSaveable { mutableIntStateOf(0) }
    var isSecondArticleRead by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val secondActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val isRead = result.data?.getBooleanExtra("IS_READ", false) ?: false
            isSecondArticleRead = isRead
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Makale")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lukas Vera 'Himki'yi 'Chelsea' ile kıyasladı",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Makale Resmi",
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Himki orta saha oyuncusu Lukas Vera, Moskova bölgesi takımını Londra'nın Chelsea'si ile karşılaştırdı.",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "- Kamuoyu Himki'yi Chelsea ile karşılaştırıyor. Oyuncu sayısı tek benzerlik mi?",
                fontSize = 14.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "- Evet öyle. Şu anda takımda çok fazla oyuncu var, ancak en önemlisi iyi çalışıyor olmamız. Herhangi bir sorun görmüyorum.",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Lukas Vera 'Himki'yi 'Chelsea' ile kıyasladı - İlginç bir makale!"
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            ) {
                Text("Makaleyi Paylaş")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Bu makaleyi beğendiniz mi?", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = { likeCount++ }) {
                    Text("Beğen ($likeCount)")
                }

                Button(onClick = { dislikeCount++ }) {
                    Text("Beğenme ($dislikeCount)")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    intent.putExtra("ARTICLE_TITLE", "Fernando Costanza: 'Himki Maçında Eksik Olan Gol Duygusu'")
                    intent.putExtra("ARTICLE_CONTENT",
                        "Brezilyalı futbolcu 'Kanatlar Sovyetov' takımından Fernando Costanza, " +
                                "Rusya Premier Ligi'nin 10. haftasında Himki ile oynanan maçın berabere bitmesini yorumladı.\n\n" +

                                "MAÇ ÖZETİ:\n" +
                                "• Himki vs Kanatlar Sovyetov\n" +
                                "• Skor: 0-0\n" +
                                "• Lig: Rusya Premier Ligi 10. Hafta\n" +
                                "• Yer: Himki Stadyumu\n\n" +

                                "COSTANZA'NIN YORUMU:\n" +
                                "\"Gol atamadık. Bugün olan buydu. Bence iyi bir maç oynadık. " +
                                "Bir şeyler yaratmaya çalıştık, topu kontrol ettik. " +
                                "Muhtemelen son pas ve şutlar en iyi seviyede değildi.\"\n\n" +

                                "MAÇIN DETAYLARI:\n" +
                                "İki takım da ofansif oynamaya çalıştı ancak gol pozisyonlarını değerlendiremedi. " +
                                "Himki savunma olarak organize bir görüntü çizerken, " +
                                "Kanatlar Sovyetov orta saha hakimiyetinde başarılıydı ancak " +
                                "son vuruşlarda isabet sağlayamadı."
                    )
                    secondActivityLauncher.launch(intent)
                }
            ) {
                Text("Sonraki Makale")
            }

            if (isSecondArticleRead) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "✓ İkinci makale okundu",
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}