package ru.urfu.droidpractice1

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val TAG_LIFECYCLE = "SecondActivityLifecycle"
    private lateinit var binding: ActivitySecondBinding
    private var isArticleRead = false

    companion object {
        const val EXTRA_ARTICLE_TITLE_FROM_MAIN = "ru.urfu.droidpractice1.ARTICLE_TITLE_FROM_MAIN"
        const val EXTRA_ARTICLE_CONTENT_FROM_MAIN = "ru.urfu.droidpractice1.ARTICLE_CONTENT_FROM_MAIN"
        const val EXTRA_IS_READ_RESULT = "ru.urfu.droidpractice1.IS_READ_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG_LIFECYCLE, "onCreate SecondActivity (Platform Theme - Direct Listener)")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(binding.toolbar as Toolbar)
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.title = "Вторая статья (Платформа)"

            binding.toolbar.setNavigationOnClickListener {
                Log.d(TAG_LIFECYCLE, "Toolbar Navigation Icon Clicked (setNavigationOnClickListener)")
                returnResultAndFinish()
            }
            Log.d(TAG_LIFECYCLE, "setNavigationOnClickListener для Toolbar установлен.")

        } else {
            Log.w(TAG_LIFECYCLE, "SDK < LOLLIPOP, платформенный ActionBar не настроен.")
        }

        val titleFromIntent = intent.getStringExtra(EXTRA_ARTICLE_TITLE_FROM_MAIN)
        if (titleFromIntent != null) {
            binding.tvArticle2Title.text = titleFromIntent
        }
        val contentFromIntent = intent.getStringExtra(EXTRA_ARTICLE_CONTENT_FROM_MAIN)
        if (contentFromIntent != null) {
            binding.tvArticle2Content.text = contentFromIntent
        }

        if (savedInstanceState != null) {
            isArticleRead = savedInstanceState.getBoolean("article_read_state_second", false)
            Log.d(TAG_LIFECYCLE, "Restored isArticleRead = $isArticleRead")
        }
        binding.switchArticleRead.isChecked = isArticleRead

        binding.switchArticleRead.setOnCheckedChangeListener { _, isChecked ->
            isArticleRead = isChecked
            Log.i("SecondActivityActions", "Статья прочитана: $isArticleRead")
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d(TAG_LIFECYCLE, "Системная кнопка 'назад' нажата (OnBackPressedCallback)")
                returnResultAndFinish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG_LIFECYCLE, "onOptionsItemSelected: item ID = ${item.itemId}")
        if (item.itemId == android.R.id.home) {
            Log.w(TAG_LIFECYCLE, "onOptionsItemSelected: android.R.id.home обработано здесь (возможно, избыточно)")
            returnResultAndFinish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun returnResultAndFinish() {
        Log.d(TAG_LIFECYCLE, "returnResultAndFinish: Попытка вернуть результат и закрыть Activity. isArticleRead = $isArticleRead")
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_IS_READ_RESULT, isArticleRead)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        Log.d(TAG_LIFECYCLE, "returnResultAndFinish: finish() вызван")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("article_read_state_second", isArticleRead)
        Log.d(TAG_LIFECYCLE, "Saved isArticleRead = $isArticleRead")
    }

    override fun onStart() { super.onStart(); Log.d(TAG_LIFECYCLE, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG_LIFECYCLE, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG_LIFECYCLE, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG_LIFECYCLE, "onStop") }
    override fun onRestart() { super.onRestart(); Log.d(TAG_LIFECYCLE, "onRestart") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG_LIFECYCLE, "onDestroy") }
}
