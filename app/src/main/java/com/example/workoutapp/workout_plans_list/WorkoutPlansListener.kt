package com.example.workoutapp.workout_plans_list

import com.example.workoutapp.model.WorkoutPlan

interface WorkoutPlansListener {
    fun onItemClick(workoutPlan: WorkoutPlan)
    fun onItemLongClick(workoutPlan: WorkoutPlan)
}