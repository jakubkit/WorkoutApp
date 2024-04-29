package com.example.workoutapp.workout_plans_list.workout_plan

import com.example.workoutapp.model.Exercise

interface ExerciseListListener {
    fun onItemClick(exercise: Exercise)
    fun onItemLongClick(exercise: Exercise)
    fun updateButtonClick(exercise: Exercise)
}