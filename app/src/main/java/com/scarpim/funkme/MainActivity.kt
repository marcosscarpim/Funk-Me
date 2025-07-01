package com.scarpim.funkme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(WindowInsets.safeDrawing.asPaddingValues())
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.FunkScreen.route,
                        ) {
                            composable(Screen.FunkScreen.route) {
                                FunkScreen(
                                    viewModel = viewModel,
                                    navigateToFilesScreen = {
                                        navController.navigate(Screen.FileScreen.route)
                                    }
                                )
                            }

                            composable(Screen.FileScreen.route) {
                                Text("TODO")
                            }
                        }
                    }
                }
            }
        }
        viewModel.onAction(FunkScreenAction.LoadAudios)
    }
}

sealed class Screen(val route: String) {
    object FunkScreen : Screen("funk")
    object FileScreen : Screen("file")
}
