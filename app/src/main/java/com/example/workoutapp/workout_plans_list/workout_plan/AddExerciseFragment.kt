package com.example.workoutapp.workout_plans_list.workout_plan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentAddExerciseBinding
import com.example.workoutapp.model.ExerciseRepository
import com.example.workoutapp.model.WorkoutPlansDatabase

class AddExerciseFragment : Fragment() {
    private lateinit var viewModel: AddExerciseViewModel
    private lateinit var binding: FragmentAddExerciseBinding
    val args: AddExerciseFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExerciseBinding.inflate(inflater, container, false)
        val workoutPlansDao = WorkoutPlansDatabase.getDatabase(requireContext()).WorkoutPlansDao()
        val exerciseRepository = ExerciseRepository(workoutPlansDao, args.workoutPlanId)
        viewModel = ViewModelProvider(this, AddExerciseViewModelFactory(exerciseRepository))[AddExerciseViewModel::class.java]
        viewModel.planId = args.workoutPlanId
        binding.viewModel = viewModel
        binding.saveButton.setOnClickListener{
            viewModel.saveExercise()
            findNavController().popBackStack(R.id.exerciseListFragment, false)
        }
        return binding.root
    }
}