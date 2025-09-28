package ru.urfu.droidpractice1

import android.content.Context
import android.content.Intent
import java.net.URL

fun shareArticle(context: Context, articleTitle: String, articleURL: String) {
    val shareText = """
        ${context.getString(R.string.titleStatya1)}
        Ссылка на статью: ${articleURL}
        """

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, articleTitle)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
}
