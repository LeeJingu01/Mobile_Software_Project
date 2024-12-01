package com.example.mobile_software_project

import android.adservices.topics.Topic
import android.provider.ContactsContract.Data
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_software_project.data.Datasource
import com.example.mobile_software_project.data.MealDataStore
import com.example.mobile_software_project.model.Affirmation
import com.example.mobile_software_project.model.FoodDetail
import com.example.mobile_software_project.model.Meal

@Composable
fun Input(modifier: Modifier = Modifier) {
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
                            calories = foodDetail.calories
                        )
                        MealDataStore.addMeal(meal) // MealDataStore에 저장
                        println("Saved data: $meal") // 로그 출력
                        selectedAffirmation = null
                    },
                    onCancel = { selectedAffirmation = null } // 취소 시 이전 화면으로
                )
            }
            selectedLocation == null -> {
                // 장소 선택 화면
                LocationSelector(
                    locations = listOf("상록원 1층", "상록원 2층", "기숙사"),
                    onLocationSelected = { location ->
                        selectedLocation = location
                    }
                )
            }
            else -> {
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


@Composable
fun AffirmationCard(topic: Affirmation, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = topic.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}
@Composable
fun LocationSelector(
    locations: List<String>,
    onLocationSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "장소를 선택하세요",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        locations.forEach { location ->
            Button(
                onClick = { onLocationSelected(location) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = location)
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "음식 정보 입력",
            style = MaterialTheme.typography.titleLarge,
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



