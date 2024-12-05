package com.example.mobile_software_project

import android.adservices.topics.Topic
import android.provider.ContactsContract.Data
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_software_project.data.Datasource
import com.example.mobile_software_project.data.MealDataStore
import com.example.mobile_software_project.model.Affirmation
import com.example.mobile_software_project.model.FoodDetail
import com.example.mobile_software_project.model.Meal

@Composable
fun Input(navController: NavController, modifier: Modifier = Modifier) {
    var selectedLocation by remember { mutableStateOf<String?>(null) } // 장소 선택 상태
    var selectedAffirmation by remember { mutableStateOf<Affirmation?>(null) } // 선택된 음식 상태

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        when {
            selectedAffirmation != null -> {
                // 입력 화면
                FoodDetailInputScreen(
                    affirmation = selectedAffirmation!!,
                    onSave = { foodDetail ->
                        // 입력된 데이터를 MealDataStore에 저장
                        val meal = Meal(
                            name = foodDetail.name,
                            date = foodDetail.date,
                            type = foodDetail.type,
                            review = foodDetail.review,
                            cost = foodDetail.cost,
                            calories = foodDetail.calories,
                            affirmation = selectedAffirmation!!
                        )
                        MealDataStore.addMeal(meal) // MealDataStore에 저장
                        selectedAffirmation = null
                        navController.navigate("StartScreen")
                    },
                    onCancel = {
                        selectedAffirmation = null
                        navController.navigate("StartScreen")
                    } // 취소 시 처음 화면으로
                )
            }
            selectedLocation == null -> {
                // 장소 선택 화면
                LocationSelector(
                    locations = listOf("상록원 1층", "상록원 2층", "기숙사"),
                    onLocationSelected = { location ->
                        selectedLocation = location
                    },
                    navController = navController
                )
            }
            else -> {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                                        navController.navigate("Input")
                                    }
                                    .size(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(25.dp))
                        Text("음식 사진 입력", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(55.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // 사진 그리드 화면
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        val affirmations = Datasource.getAffirmationsByLocation(selectedLocation!!)
                        items(affirmations) { topic ->
                            AffirmationCard(
                                topic = topic,
                                onClick = { selectedAffirmation = topic } // 사진 클릭 이벤트
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AffirmationCard(topic: Affirmation, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = topic.imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clickable { onClick() }
            .size(170.dp)
            .border(
                BorderStroke(7.dp, Color.Gray),
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun LocationSelector(
    locations: List<String>,
    onLocationSelected: (String) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(){
            Column{
                Spacer(modifier = Modifier.height(8.dp))
                // 뒤로가기 버튼
                Image(
                    painter = painterResource(R.drawable.left_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable{ navController.navigate("StartScreen")}
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = "장소를 선택하세요",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Spacer(modifier = Modifier.width(55.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        locations.forEach { location ->
            Button(
                onClick = { onLocationSelected(location) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = location,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun FoodDetailInputScreen(
    affirmation: Affirmation,
    onSave: (Meal) -> Unit,
    onCancel: () -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var foodReview by remember { mutableStateOf("") }
    var foodDate by remember { mutableStateOf("") }
    var mealType by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "음식 정보 입력",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 음식 이름
        TextField(
            value = foodName,
            onValueChange = { foodName = it },
            label = { Text("음식 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        // 음식 소감
        TextField(
            value = foodReview,
            onValueChange = { foodReview = it },
            label = { Text("음식 소감") },
            modifier = Modifier.fillMaxWidth()
        )

        // 날짜 입력
        TextField(
            value = foodDate,
            onValueChange = { foodDate = it },
            label = { Text("날짜 (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        // 식사 종류
        TextField(
            value = mealType,
            onValueChange = { mealType = it },
            label = { Text("식사 종류 (조식, 중식, 석식, 간식/음료)") },
            modifier = Modifier.fillMaxWidth()
        )

        // 비용 입력
        TextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("비용 (원)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // 저장 및 취소 버튼
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val meal = Meal(
                        name = foodName,
                        review = foodReview,
                        date = foodDate,
                        type = mealType,
                        cost = cost.toIntOrNull() ?: 0,
                        affirmation = affirmation
                    )
                    onSave(meal)
                }
            ) {
                Text("저장")
            }
            Button(onClick = { onCancel() }) {
                Text("취소")
            }
        }
    }
}



