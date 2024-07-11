package com.example.workoutapp.search_exercises
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.SearchExerciseListItemBinding

class SearchExercisesAdapter:
    ListAdapter<ExampleExercise, SearchExercisesAdapter.ViewHolder>(SearchExercisesAdapter.SearchExercisesDiffCallback()){
    inner class ViewHolder(private val binding: SearchExerciseListItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(exercise: ExampleExercise){
                binding.exercise = exercise
                binding.executePendingBindings()
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchExercisesAdapter.ViewHolder {
        val binding = SearchExerciseListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchExercisesAdapter.ViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }
    private class SearchExercisesDiffCallback: DiffUtil.ItemCallback<ExampleExercise>() {
        override fun areItemsTheSame(oldItem: ExampleExercise, newItem: ExampleExercise): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ExampleExercise, newItem: ExampleExercise): Boolean {
            return oldItem == newItem
        }
    }
}