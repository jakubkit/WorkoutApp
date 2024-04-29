package com.example.workoutapp.workout_plans_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.model.WorkoutPlan
import com.example.workoutapp.model.WorkoutPlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWorkoutPlanViewModel(
    private val workoutPlansRepository: WorkoutPlansRepository
): ViewModel() {
    val titleInput = MutableLiveData<String>()

    private fun insertWorkoutPlan(workoutPlan: WorkoutPlan){
        viewModelScope.launch(Dispatchers.IO) {
            workoutPlansRepository.insertWorkoutPlan(workoutPlan)
        }
    }
    fun updateWorkoutPlan(){
        TODO("updatowanie")
    }

    fun saveWorkoutPlan(){
        val titleValue = titleInput.value ?: return
        val workoutPlanObj = WorkoutPlan(titleValue)
        insertWorkoutPlan(workoutPlanObj)
        titleInput.value = ""
    }
}

class AddWorkoutPlanViewModelFactory(private val workoutPlansRepository: WorkoutPlansRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddWorkoutPlanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddWorkoutPlanViewModel(workoutPlansRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}