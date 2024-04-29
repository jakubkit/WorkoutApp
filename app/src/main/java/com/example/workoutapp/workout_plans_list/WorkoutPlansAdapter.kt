package com.example.workoutapp.workout_plans_list


import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.workoutapp.model.WorkoutPlan
import com.example.workoutapp.databinding.WorkoutPlanItemBinding

class WorkoutPlansAdapter(
    val eventListener: WorkoutPlansListener):
    ListAdapter<WorkoutPlan, WorkoutPlansAdapter.ViewHolder>(WorkoutPlansDiffCallback()){
    inner class ViewHolder(private val binding: WorkoutPlanItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(workoutPlan: WorkoutPlan){
                    binding.workoutPlan = workoutPlan
                    binding.executePendingBindings()
                    binding.root.setOnClickListener{
                        eventListener.onItemClick(workoutPlan)
                    }
                    binding.root.setOnLongClickListener{
                        eventListener.onItemLongClick(workoutPlan)
                        true
                    }
                    binding.executePendingBindings()
                }
            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WorkoutPlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = getItem(position)
        holder.bind(plan)
    }

    private class WorkoutPlansDiffCallback: DiffUtil.ItemCallback<WorkoutPlan>() {
            override fun areItemsTheSame(oldItem: WorkoutPlan, newItem: WorkoutPlan): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: WorkoutPlan, newItem: WorkoutPlan): Boolean {
                return oldItem == newItem
            }
        }
}
