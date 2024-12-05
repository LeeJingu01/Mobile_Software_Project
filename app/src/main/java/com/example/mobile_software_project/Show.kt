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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mobile_software_project.data.MealDataStore
import com.example.mobile_software_project.model.Meal

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
    ){
        Spacer(modifier = Modifier.height(66.dp))
        Row() {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                // 뒤로가기 버튼
                Image(
                    painter = painterResource(R.drawable.left_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            navController.navigate("StartScreen")
                        }
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(25.dp))
            Text("음식 리스트", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(55.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meals) { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMealClick(meal) } // 클릭 이벤트
                        .padding(8.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ){
                        Image(
                            painter = painterResource(meal.affirmation.imageRes), // 음식 사진
                            contentDescription = "",
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column(
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(text = meal.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "날짜: ${meal.date}", fontSize = 14.sp)
                            Text(text = "종류: ${meal.type}", fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "${meal.calories} kcal", fontSize = 14.sp)
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "음식 이름: ${meal.name}", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
        Text(text = "날짜: ${meal.date}", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Text(text = "종류: ${meal.type}", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Text(text = "리뷰: ${meal.review}", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Text(text = "비용: ${meal.cost}원", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Text(text = "칼로리: ${meal.calories} kcal", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.material3.Button(onClick = { onBack() }) {
            Text(text = "뒤로 가기")
        }
    }
}