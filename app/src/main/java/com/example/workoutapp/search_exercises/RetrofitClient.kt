package com.example.workoutapp.search_exercises

import androidx.lifecycle.LiveData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

object RetrofitClient {
    private const val BASE_URL = "https://gym-fit.p.rapidapi.com/"
    const val apiKey = "4f4f359295msh1b8170fb8370daap13f57djsnbe3b71dfc2d2"
    const val apiHost = "gym-fit.p.rapidapi.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)
        }
        .build()

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
        return retrofit!!
    }
}

interface ExerciseService {
    @GET("exercises/search")
    suspend fun searchExercises(
        @Query("bodyPart") bodyPart: String,
    ): Response<List<ExampleExercise>>
}

data class Item(
    @Json(name = "name") val name: String,
    @Json(name = "bodyParts") val bodyParts: List<String>
)

@JsonClass(generateAdapter = true)
data class ExampleExercise(
    val name: String,
    var bodyParts: List<String>
)