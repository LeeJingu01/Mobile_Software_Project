package com.example.mobile_software_project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_software_project.data.MealDataStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Analysis(
    navController: NavController
) {
    val meals = MealDataStore.getMeals()

    // 현재 날짜와 1달 전 날짜 계산
    val currentDate = LocalDate.now()
    val oneMonthAgo = currentDate.minusDays(30)
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // 최근 1달 간의 식사 필터링
    val recentMeals = meals.filter { meal ->
        val mealDate = LocalDate.parse(meal.date, dateFormatter)
        mealDate.isAfter(oneMonthAgo) || mealDate.isEqual(oneMonthAgo)
    }

    // 총 칼로리 계산
    val totalCalories = recentMeals.sumOf { it.calories }

    // 종류별 비용 합산
    val costByType = recentMeals.groupBy { it.type }
        .mapValues { (_, meals) -> meals.sumOf { it.cost } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // 헤더
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "",
                modifier = Modifier
                    .clickable { navController.navigate("StartScreen") }
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "식사 분석",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // 총 칼로리 섹션
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "최근 1달 간 총 칼로리",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$totalCalories kcal",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4CAF50) // 초록색 강조
                )
            }
        }

        // 종류별 비용 섹션
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "종류별 비용 분석",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(costByType.toList()) { (type, cost) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = type,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${cost}원",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}
