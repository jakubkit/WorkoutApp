package com.example.workoutapp.workout_plans_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentAddWorkoutPlanBinding
import com.example.workoutapp.model.Exercise
import com.example.workoutapp.model.WorkoutPlansDatabase
import com.example.workoutapp.model.WorkoutPlansRepository

class AddWorkoutPlanFragment() : Fragment() {
    private lateinit var viewModel: AddWorkoutPlanViewModel
    private lateinit var binding: FragmentAddWorkoutPlanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWorkoutPlanBinding.inflate(layoutInflater, container, false)
        val workoutPlanDao = WorkoutPlansDatabase.getDatabase(requireContext()).WorkoutPlansDao()
        val workoutPlansRepository = WorkoutPlansRepository(workoutPlanDao)
        viewModel = ViewModelProvider(this, AddWorkoutPlanViewModelFactory(workoutPlansRepository))[AddWorkoutPlanViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.saveButton.setOnClickListener{
            viewModel.saveWorkoutPlan()
            findNavController().popBackStack(R.id.yourWorkoutsFragment,false)
        }
        return binding.root
    }
}