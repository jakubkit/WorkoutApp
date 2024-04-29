package com.example.workoutapp.workout_plans_list

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView


import com.example.workoutapp.model.WorkoutPlan
import com.example.workoutapp.model.WorkoutPlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YourWorkoutsViewModel(
    private val workoutPlansRepository: WorkoutPlansRepository
): ViewModel()
{
    val allWorkoutPlans: LiveData<List<WorkoutPlan>> = workoutPlansRepository.workoutPlansItems

}
class YourWorkoutsViewModelFactory(private val workoutPlansRepository: WorkoutPlansRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YourWorkoutsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YourWorkoutsViewModel(workoutPlansRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
@BindingAdapter("app:workoutPlansItems")
fun setItems(recyclerView: RecyclerView, items: List<WorkoutPlan>?){
    items?.let{
        if(recyclerView.adapter is WorkoutPlansAdapter){
            (recyclerView.adapter as WorkoutPlansAdapter).submitList(items)
        }
    }
}
