package com.example.workoutapp.search_exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.FragmentSearchExercisesBinding
import com.example.workoutapp.model.Exercise
import com.example.workoutapp.workout_plans_list.workout_plan.ExerciseListAdapter
import com.example.workoutapp.workout_plans_list.workout_plan.ExerciseListListener
import com.google.android.material.snackbar.Snackbar

class SearchExercisesFragment : Fragment() {

    private lateinit var viewModel: SearchExercisesViewModel
    private lateinit var binding: FragmentSearchExercisesBinding
    private lateinit var listAdapter: SearchExercisesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchExercisesBinding.inflate(inflater, container, false)
        val exerciseService = RetrofitClient.getClient().create(ExerciseService::class.java)

        viewModel = ViewModelProvider(this, SearchExercisesViewModelFactory(exerciseService)).get(SearchExercisesViewModel::class.java)
        binding.viewModel = viewModel
        setupRecycleView()
//        // Observe LiveData from ViewModel
        viewModel.exercises.observe(this){ exercises ->
            listAdapter.submitList(exercises)
        }
        // Trigger API call
        binding.button.setOnClickListener{
            viewModel.bodyPart.value = binding.bodyPartSpinner.selectedItem.toString()
            viewModel.searchExercises()
        }
        return binding.root
    }
    private fun setupRecycleView() {
        // sharedViewModel = ViewModelProvider(this, DeleteDialogViewModelFactory(workoutPlansRepository))[DeleteDialogViewModel::class.java]
        listAdapter = SearchExercisesAdapter()
        binding.exercisesList.layoutManager = LinearLayoutManager(context)
        binding.exercisesList.adapter = listAdapter
    }
}