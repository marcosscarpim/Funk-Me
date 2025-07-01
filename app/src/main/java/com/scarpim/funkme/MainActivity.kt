package com.scarpim.funkme

import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
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
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(WindowInsets.safeDrawing.asPaddingValues())
                    ) {
                        FunkScreen(viewModel = viewModel)
                    }
                }
            }
        }
        viewModel.onAction(FunkScreenAction.LoadAudios)
    }

    private fun setStatusBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val statusBarInsets = insets.getInsets(android.view.WindowInsets.Type.statusBars())
                val navBarInsets = insets.getInsets(android.view.WindowInsets.Type.navigationBars())
                view.setBackgroundColor(color)

                // Adjust padding to avoid overlap
                view.setPadding(0, statusBarInsets.top, 0, navBarInsets.bottom)
                insets
            }
        } else {
            // For Android 14 and below
            window.statusBarColor = color
        }
    }
}
