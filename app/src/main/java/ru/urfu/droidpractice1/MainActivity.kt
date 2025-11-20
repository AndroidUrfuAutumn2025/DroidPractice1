package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import ru.urfu.droidpractice1.content.ArticleViewModel
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {

    private val vm: ArticleViewModel by viewModels()

    // Получаем результат от SecondActivity (прочитано/не прочитано)
    private val secondResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val read = result.data?.getBooleanExtra(SecondActivity.EXTRA_READ, false) ?: false
        vm.setSecondRead(read)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle-Main", "onCreate")
        setContent {
            MainActivityScreen(
                vm = vm,
                onOpenSecond = {
                    val intent = Intent(this, SecondActivity::class.java)
                    secondResultLauncher.launch(intent)
                },
                onShare = { text ->
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.share_article)))
                }
            )
        }
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle-Main", "onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle-Main", "onResume") }
    override fun onPause() { super.onPause(); Log.d("Lifecycle-Main", "onPause") }
    override fun onStop() { super.onStop(); Log.d("Lifecycle-Main", "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("Lifecycle-Main", "onDestroy") }
}
