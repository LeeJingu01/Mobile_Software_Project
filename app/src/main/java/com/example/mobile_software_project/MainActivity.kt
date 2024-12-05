package com.example.mobile_software_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_software_project.ui.theme.Mobile_Software_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobile_Software_ProjectTheme {
                MainApp()
            }
        }
    }
}
@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "StartScreen") {
        // Main 화면
        composable("StartScreen") {
            StartScreen(navController)
        }
        // 식사 입력 화면
        composable("input") {
            Input(navController)
        }
        // 식사 리스트 화면
        composable("show") {
            Show(navController)
        }
        // 식사 분석 화면
        composable("analysis") {
            Analysis(navController)
        }
    }
}




