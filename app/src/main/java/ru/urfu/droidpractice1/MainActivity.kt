package ru.urfu.droidpractice1

import android.content.ContentProvider
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.urfu.droidpractice1.content.MainActivityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }

    @Composable
    private fun MainActivityContent(){
        Column(Modifier.fillMaxSize()){
            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = { }
            ) {
                Text(text = "Click")
            }
        }
    }

    @Preview
    @Composable
    private fun ContentProvider(){
        MainActivityContent()
    }


}