package com.example.workoutapp.workout_plans_list.workout_plan

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.model.Exercise
import com.example.workoutapp.model.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseListViewModel(
    private val exerciseRepository: ExerciseRepository
): ViewModel() {
    val exerciseItems: LiveData<MutableList<Exercise>> = exerciseRepository.exerciseListItems

    fun updateExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.updateExercise(exercise)
        }
    }
    fun deleteExercises(){
        TODO("DELETE CWICZEN DO BAZY")
    }


}
class ExerciseListViewModelFactory(private val exerciseRepository: ExerciseRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExerciseListViewModel(exerciseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//@BindingAdapter("app:exerciseItems")
//fun setItems(recyclerView: RecyclerView, items: List<Exercise>?){
//    items?.let{
//        if(recyclerView.adapter is ExerciseListAdapter){
//            (recyclerView.adapter as ExerciseListAdapter).submitList(items)
//        }
//    }
//}
