package com.example.workoutapp.workout_plans_list.workout_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.model.Exercise
import com.example.workoutapp.model.ExerciseRepository
import com.example.workoutapp.model.WorkoutPlansRepository
import com.example.workoutapp.workout_plans_list.AddWorkoutPlanViewModel
import kotlinx.coroutines.launch

class AddExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    val nameInput = MutableLiveData<String>()
    val setsInput = MutableLiveData<String>()
    val repsInput = MutableLiveData<String>()
    val weightInput = MutableLiveData<String>()
    val notesInput = MutableLiveData<String>()
    var planId: Int = 0
    private fun insertExercise(exercise: Exercise){
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }

    fun saveExercise(){
        val name = nameInput.value ?: return
        val sets = setsInput.value ?: return
        val reps = repsInput.value ?: return
        val weight = weightInput.value ?: return
        val notes = notesInput.value ?: return
        val exerciseObj = Exercise(name, planId, sets.toInt(), reps.toInt(), weight.toDouble(), notes)
        insertExercise(exerciseObj)
        nameInput.value = ""
        setsInput.value = ""
        repsInput.value = ""
        weightInput.value = ""
        notesInput.value = ""
    }

}

class AddExerciseViewModelFactory(private val exerciseRepository: ExerciseRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExerciseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddExerciseViewModel(exerciseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}