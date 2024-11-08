package com.example.restaurantsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.restaurantsapp.models.Restaurant
import com.example.restaurantsapp.network.api.ApiRepository
import kotlinx.coroutines.launch


@Composable
fun RestaurantsScreen() {
    var restaurants by remember { mutableStateOf<List<Restaurant>>(emptyList()) }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val apiRepository = ApiRepository()

    fun loadRestaurants() {
        showLoading = true
        errorMessage = null

        scope.launch {
            try {
                val response = apiRepository.getRestaurants()
                restaurants = response
            } catch (ex: Exception) {
                errorMessage = "Error loading restuarants ${ex.message}"
            } finally {
                showLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        loadRestaurants()
    }

    //Интерфейс
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Restaurants",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if(showLoading == true) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.CenterHorizontally))
        }

        errorMessage?.let { message ->
            Text(
                text = message,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(restaurants) { restaurant ->
                RestaurantItem(restaurant = restaurant)
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = restaurant.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = restaurant.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}