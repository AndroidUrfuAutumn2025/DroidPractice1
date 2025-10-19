package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : BaseActivity() {
    private var isNextArticleRead by mutableStateOf(false)

    private fun shareArticle(content: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, content)
        }
        startActivity(Intent.createChooser(shareIntent, "Share"))
    }

    private fun handleRate(
        targetRateState: MutableIntState,
        otherRateState: MutableIntState,
        isTargetChosen: MutableState<Boolean>,
        isOtherChosen: MutableState<Boolean>,
    ) {
        if (!isOtherChosen.value) {
            if (!isTargetChosen.value) {
                targetRateState.intValue++
            } else {
                targetRateState.intValue--
            }
            isTargetChosen.value = !isTargetChosen.value
        } else {
            otherRateState.intValue--
            targetRateState.intValue++
            isOtherChosen.value = false
            isTargetChosen.value = true
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            isNextArticleRead = result.data?.getBooleanExtra("IS_READ", false) == true
        }
    }

    private fun openSecondActivityForResult() {
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("IS_READ", isNextArticleRead)
        }
        resultLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        isNextArticleRead = savedInstanceState?.getBoolean("IS_READ", false) == true
        setContent {
            MainActivityScreen(
                onShareArticle = ::shareArticle,
                handleRate = ::handleRate,
                onOpenNextArticle = { openSecondActivityForResult() },
                isNextRead = isNextArticleRead
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState")
        outState.putBoolean("IS_READ", isNextArticleRead)
        super.onSaveInstanceState(outState)
    }
}