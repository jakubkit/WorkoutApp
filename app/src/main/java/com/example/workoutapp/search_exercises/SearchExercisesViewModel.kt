package com.example.workoutapp.search_exercises

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class SearchExercisesViewModel(private val exerciseService: ExerciseService) : ViewModel() {

    // LiveData to hold the list of exercises
    private val _exercises = MutableLiveData<List<ExampleExercise>>()
    val exercises: LiveData<List<ExampleExercise>> = _exercises
    var bodyPart = MutableLiveData<String>()
    // Function to search exercises based on body part
    fun searchExercises() {
        val bdP = bodyPart.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val response = exerciseService.searchExercises(bdP)
            if (response.isSuccessful) {
                val exercisesResponse = response.body()
                _exercises.postValue(exercisesResponse)
            }
        }

    }
}

class SearchExercisesViewModelFactory(private val exerciseService: ExerciseService): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchExercisesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchExercisesViewModel(exerciseService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}