package ru.urfu.droidpractice1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

class SecondActivity : ComponentActivity() {

    companion object {
        const val KEY_READ_STATE = "read_state_second"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidPractice1Theme {
                SecondArticleScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondArticleScreen() {
    val context = LocalContext.current
    var isRead by remember {
        mutableStateOf(
            context.getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
                .getBoolean(SecondActivity.KEY_READ_STATE, false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Квантовые вычисления") },
                navigationIcon = {
                    IconButton(onClick = { 
                        (context as ComponentActivity).finish()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Заголовок
            Text(
                text = "Квантовые вычисления: революция в обработке данных",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Подзаголовок
            Text(
                text = "Как квантовые компьютеры изменят мир вычислений и какие вызовы стоят перед исследователями",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Основной текст
            Text(
                text = "Квантовые вычисления представляют собой принципиально новый подход к обработке информации, основанный на законах квантовой механики. В отличие от классических битов, которые могут находиться только в состоянии 0 или 1, квантовые биты (кубиты) могут существовать в суперпозиции состояний, что позволяет обрабатывать информацию принципиально иным способом.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 24.sp
            )

            // Ключевые преимущества
            Text(
                text = "Ключевые преимущества",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "• Квантовое превосходство: способность решать задачи, недоступные классическим компьютерам\n• Параллелизм: одновременная обработка множества состояний\n• Сверхпроводящие кубиты: высокая скорость операций\n• Квантовая запутанность: корреляция между кубитами",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 22.sp
            )

            // Цитата
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "\"Квантовые компьютеры не просто быстрее — они решают задачи, которые классические компьютеры принципиально не могут решить за разумное время.\"",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Области применения
            Text(
                text = "Области применения",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Квантовые вычисления найдут применение в различных областях:\n\n• Криптография: взлом современных шифров и создание квантово-устойчивых алгоритмов\n• Молекулярное моделирование: разработка новых лекарств и материалов\n• Оптимизация: решение сложных логистических и финансовых задач\n• Машинное обучение: ускорение тренировки нейросетей",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 22.sp
            )

            Text(
                text = "Несмотря на значительный прогресс, квантовые вычисления сталкиваются с серьезными вызовами: декогеренция, ошибки квантовых операций и масштабирование систем. Однако исследователи уверены, что эти проблемы будут решены в ближайшие годы.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 22.sp
            )

            // Переключатель прочитано
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Отметить как прочитанное",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Switch(
                        checked = isRead,
                        onCheckedChange = { checked ->
                            isRead = checked
                            val prefs = context.getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
                            prefs.edit().putBoolean(SecondActivity.KEY_READ_STATE, checked).apply()
                            
                            // Устанавливаем результат для возврата в MainActivity
                            (context as ComponentActivity).setResult(
                                android.app.Activity.RESULT_OK,
                                Intent().apply {
                                    putExtra(SecondActivity.KEY_READ_STATE, checked)
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}
