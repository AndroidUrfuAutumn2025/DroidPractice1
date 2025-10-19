package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : BaseActivity() {

    private lateinit var binding: ActivitySecondBinding

    private val articleHeading = "Phasellus commodo sed metus vitae consectetur"
    private val articleContent = listOf<String>(
        """
            Phasellus commodo sed metus vitae consectetur. Ut ullamcorper ex vel suscipit gravida. Quisque imperdiet ex sit amet ligula blandit, in consectetur metus fermentum. Nunc condimentum leo quis magna sagittis, lobortis semper nunc varius. Nunc imperdiet nisl vitae semper porta.
        """.trimIndent(),
        "https://i.ytimg.com/vi/4h4FIL0Ivvg/maxresdefault.jpg",
        """
            Aliquam ultrices nibh ac massa molestie, et consectetur justo viverra. Sed elementum 
            ligula congue tortor gravida faucibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc tellus odio, tristique ut iaculis non, lobortis ac diam. In et auctor enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Praesent porta velit risus, at posuere elit pharetra vitae.
        """.trimIndent(),
        """
            Proin varius, arcu sed imperdiet ultrices, est lacus vestibulum ipsum, eget sodales 
            tellus risus eu sapien. Aenean a nisi ut augue efficitur rutrum ac ac odio. Mauris laoreet facilisis lorem, eget sollicitudin lorem aliquam non. Mauris imperdiet leo non augue venenatis molestie et non leo. Pellentesque a massa maximus, aliquam lorem rhoncus, lacinia mi. Sed maximus mollis dictum. Nam viverra bibendum lectus. Quisque cursus bibendum metus sit amet tincidunt. Pellentesque at lorem vitae ex ultrices euismod.
        """.trimIndent()
    )

    private var isRead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        isRead =
            if (intent.hasExtra("IS_READ")) {
                intent.getBooleanExtra("IS_READ", false)
            } else {
                savedInstanceState?.getBoolean("IS_READ", false) == true
            }
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("IS_READ", isRead)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.heading.text = articleHeading

        val container = binding.container
        var lastContentIndex = container.indexOfChild(binding.heading)

        articleContent.forEach { element ->
            if (element.startsWith("https://")) {
                val newImage = ImageView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(
                        paddingLeft,
                        paddingTop,
                        paddingRight,
                        resources.getDimension(R.dimen.text_padding).toInt()
                    )
                }

                Glide.with(this)
                    .asBitmap()
                    .load(element)
                    .into(newImage)

                container.addView(newImage, lastContentIndex + 1)
                lastContentIndex++
            } else {
                val newParagraph = TextView(this).apply {
                    setTextAppearance(
                        com.google.android.material.R.style.TextAppearance_Material3_BodyLarge
                    )
                    text = element
                    lineHeight = resources.getDimension(R.dimen.line_height).toInt()
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(
                        paddingLeft,
                        paddingTop,
                        paddingRight,
                        resources.getDimension(R.dimen.text_padding).toInt()
                    )
                }

                container.addView(newParagraph, lastContentIndex + 1)
                lastContentIndex++
            }
        }

        binding.readSwitch.isChecked = isRead
        binding.readSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRead = isChecked
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState")
        outState.putBoolean("IS_READ", isRead)
        super.onSaveInstanceState(outState)
    }
}