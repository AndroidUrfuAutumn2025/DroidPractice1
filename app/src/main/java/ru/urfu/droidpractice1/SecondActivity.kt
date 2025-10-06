package ru.urfu.droidpractice1

import android.content.Intent
import android.graphics.Typeface
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat

class SecondActivity : BaseActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var isRead = false
    private lateinit var readSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Получаем состояние из Intent
        isRead = intent.getBooleanExtra("isRead", false)

        binding.toolbar.title = "Статья"
        binding.toolbar.setNavigationOnClickListener {
            // Возвращаем результат с состоянием прочитано
            val resultIntent = Intent()
            resultIntent.putExtra("isRead", isRead)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        // Создаем новый текст с нужным стилем
        val titleTextView = TextView(this).apply {
            text = "Игрок команды в один день смог забить гол, поймать рыбу и родить ребенка"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
            setTypeface(null, Typeface.BOLD)
            setTextColor(resources.getColor(android.R.color.black, theme))

            val horizontalPaddingDp = 16
            val horizontalPaddingPx = (horizontalPaddingDp * resources.displayMetrics.density).toInt()
            setPadding(horizontalPaddingPx, 0, horizontalPaddingPx, 0)
        }

        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.fishing)

            // Создаем скругленные углы
            val radius = 20f // Радиус скругления в пикселях
            val shape = GradientDrawable()
            shape.cornerRadius = radius
            background = shape
            clipToOutline = true

            // Устанавливаем размер изображения
            layoutParams = LinearLayout.LayoutParams(
                (350 * resources.displayMetrics.density).toInt(), // Ширина 300dp
                (200 * resources.displayMetrics.density).toInt()  // Высота 200dp
            ).apply {
                gravity = Gravity.CENTER
                // Отступы: слева 20dp, сверху 20dp, справа 20dp, снизу 20dp
                val marginDp = 20
                val marginPx = (marginDp * resources.displayMetrics.density).toInt()
                setMargins(marginPx, marginPx, marginPx, marginPx)
            }

            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        // Создаем текст под фотографией с рамкой
        val descriptionTextView = TextView(this).apply {
            text = "Да, в это сложно поверить, но такой человек существует. Его имя Льюис Халл и он проживает в Ливерпуле и играет там. Наравне с футбольными талантами он обладает многозадачностью"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(resources.getColor(android.R.color.black, theme))

            val horizontalPaddingDp = 16
            val verticalPaddingDp = 12
            val horizontalPaddingPx = (horizontalPaddingDp * resources.displayMetrics.density).toInt()
            val verticalPaddingPx = (verticalPaddingDp * resources.displayMetrics.density).toInt()
            setPadding(horizontalPaddingPx, verticalPaddingPx, horizontalPaddingPx, verticalPaddingPx)

            // Выравнивание текста по ширине
            gravity = Gravity.START

            // Создаем рамку для текста
            val border = GradientDrawable().apply {
                setColor(ContextCompat.getColor(context, android.R.color.transparent)) // Прозрачный фон
                setStroke(
                    (2 * resources.displayMetrics.density).toInt(), // Толщина рамки 2dp
                    ContextCompat.getColor(context, android.R.color.darker_gray) // Цвет рамки
                )
                cornerRadius = 12f // Скругление углов 12dp
            }
            background = border
        }

        // Создаем переключатель "Прочитано"
        readSwitch = Switch(this).apply {
            text = "Прочитано"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            isChecked = isRead // Устанавливаем начальное состояние

            // Настраиваем отступы
            val horizontalPaddingDp = 5
            val verticalPaddingDp = 10
            val horizontalPaddingPx = (horizontalPaddingDp * resources.displayMetrics.density).toInt()
            val verticalPaddingPx = (verticalPaddingDp * resources.displayMetrics.density).toInt()
            setPadding(horizontalPaddingPx, verticalPaddingPx, horizontalPaddingPx, verticalPaddingPx)

            // Обработчик изменения состояния
            setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                isRead = isChecked // Сохраняем состояние
            }
        }

        val mainLayout = binding.root as LinearLayout
        val textLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(20, 20, 20, 0)
        }

        val descriptionLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            val marginDp = 20
            val marginPx = (marginDp * resources.displayMetrics.density).toInt()
            setMargins(marginPx, 0, marginPx, 10)
        }

        val switchLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            val marginDp = 20
            val marginPx = (marginDp * resources.displayMetrics.density).toInt()
            setMargins(marginPx, 0, marginPx, marginPx)
            gravity = Gravity.START
        }

        // Добавляем элементы в layout
        mainLayout.addView(titleTextView, 1, textLayoutParams)
        mainLayout.addView(imageView, 2)
        mainLayout.addView(descriptionTextView, 3, descriptionLayoutParams)
        mainLayout.addView(readSwitch, 4, switchLayoutParams)
    }

    override fun onBackPressed() {
        // Возвращаем результат при нажатии назад
        val resultIntent = Intent()
        resultIntent.putExtra("isRead", isRead)
        setResult(RESULT_OK, resultIntent)
        super.onBackPressed()
    }
}