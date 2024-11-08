package com.example.restaurantsapp.network.api

import com.example.restaurantsapp.models.Restaurant
import com.example.restaurantsapp.network.api.ApiClient.client
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiRepository {
    suspend fun getRestaurants(): List<Restaurant> = client.get(ApiRoutes.RESTAURANTS).body()
}