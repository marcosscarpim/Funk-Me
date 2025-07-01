package com.scarpim.funkme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.scarpim.funkme.funkScreen.FunkScreen
import com.scarpim.funkme.funkScreen.FunkScreenAction
import com.scarpim.funkme.funkScreen.FunkViewModel
import com.scarpim.funkme.ui.theme.FunkMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: FunkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FunkMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FunkScreen(viewModel = viewModel)
                }
            }
        }
        viewModel.onAction(FunkScreenAction.LoadAudios)
    }
}
