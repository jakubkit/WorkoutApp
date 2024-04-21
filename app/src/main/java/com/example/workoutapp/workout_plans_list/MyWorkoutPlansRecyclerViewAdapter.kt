package com.example.workoutapp.workout_plans_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.workoutapp.databinding.FragmentWorkoutPlanBinding

import com.example.workoutapp.workout_plans_list.placeholder.PlaceholderContent.PlaceholderItem

class MyWorkoutPlansRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
)
    : RecyclerView.Adapter<MyWorkoutPlansRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentWorkoutPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = "text"
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentWorkoutPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}