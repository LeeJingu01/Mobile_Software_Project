package com.example.mobile_software_project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mobile_software_project.data.MealDataStore
import com.example.mobile_software_project.model.Meal
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color

@Composable
fun Show(
    navController: NavController
) {
    var selectedMeal by remember{ mutableStateOf<Meal?>(null) } // 선택된 식사 상태

    if (selectedMeal != null) {
        // 선택된 식사 상세 화면
        MealDetailScreen(
            meal = selectedMeal!!,
            onBack = { selectedMeal = null } // 뒤로 가기
        )
    } else {
        // 식사 리스트 화면
        MealListScreen(
            meals = MealDataStore.getMeals(),
            onMealClick = { meal -> selectedMeal = meal },
            navController = navController
        )
    }
}
@Composable
fun MealListScreen(meals: List<Meal>, onMealClick: (Meal) -> Unit, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(66.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // 뒤로 가기 버튼
            Image(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        navController.navigate("StartScreen")
                    }
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("음식 리스트", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(meals) { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMealClick(meal) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp) // 둥근 모서리
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(meal.affirmation.imageRes), // 음식 사진
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape), // 둥근 이미지
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = meal.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "날짜: ${meal.date}", fontSize = 14.sp, color = Color.Gray)
                            Text(text = "종류: ${meal.type}", fontSize = 14.sp, color = Color.Gray)
                            Text(text = "${meal.calories} kcal", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun MealDetailScreen(meal: Meal, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 뒤로 가기 버튼과 제목
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "",
                modifier = Modifier
                    .clickable { onBack() }
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "상세 정보",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 상세 카드
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "음식 이름: ${meal.name}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "날짜: ${meal.date}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "종류: ${meal.type}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "리뷰: ${meal.review}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "비용: ${meal.cost}원",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.Black
                )
                Text(
                    text = "칼로리: ${meal.calories} kcal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}
