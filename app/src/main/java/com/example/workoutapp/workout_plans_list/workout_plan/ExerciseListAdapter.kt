package com.example.workoutapp.workout_plans_list.workout_plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ExerciseListItemBinding
import com.example.workoutapp.model.Exercise
class ExerciseListAdapter(
    val eventListener: ExerciseListListener
):
    ListAdapter<Exercise, ExerciseListAdapter.ViewHolder>(ExerciseListAdapter.ExerciseListDiffCallback()){
    inner class ViewHolder(private val binding: ExerciseListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(exercise: Exercise){
            binding.exercise = exercise
            binding.executePendingBindings()
            binding.root.setOnLongClickListener{
                eventListener.onItemLongClick(exercise)
                true
            }
            binding.updateExercise.setOnClickListener{
                val temp = Exercise(binding.exerciseName.text.toString(), exercise.workoutPlanID,
                    binding.sets.text.toString().toInt(), binding.repetitions.text.toString().toInt(),
                    binding.weight.text.toString().toDouble(), binding.notes.text.toString(),
                    exercise.exerciseId)
                binding.exercise = temp
                binding.executePendingBindings()
                eventListener.updateButtonClick(exercise)
            }
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListAdapter.ViewHolder {
        val binding = ExerciseListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseListAdapter.ViewHolder, position: Int) {
        val plan = getItem(position)
        holder.bind(plan)
    }

    private class ExerciseListDiffCallback: DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.exerciseId == newItem.exerciseId
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }
    }
}