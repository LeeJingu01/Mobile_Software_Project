package com.example.mobile_software_project

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("input") }) {
            Text("식사 입력하기")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("show") }) {
            Text("식사 보여주기")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("analysis") }) {
            Text("식사 분석하기")
        }
    }
}