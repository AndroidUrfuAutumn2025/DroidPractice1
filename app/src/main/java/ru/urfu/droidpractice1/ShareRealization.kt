package ru.urfu.droidpractice1

import android.content.Context
import android.content.Intent

fun shareArticle(context: Context, articleTitle: String, articleUrl: String = "") {
    val shareText = """
        ${articleTitle}
        
        Читайте полную статью: ${if (articleUrl.isNotEmpty()) ": $articleUrl" else ""}
    """.trimIndent()

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, articleTitle)
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
}