package com.example.workoutapp.workout_plans_list.workout_plan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.FragmentExerciseListBinding
import com.example.workoutapp.model.Exercise
import com.example.workoutapp.model.ExerciseRepository
import com.example.workoutapp.model.WorkoutPlansDatabase
import com.example.workoutapp.model.WorkoutPlansRepository
import com.example.workoutapp.workout_plans_list.YourWorkoutsFragmentDirections
import com.google.android.material.snackbar.Snackbar

class ExerciseListFragment : Fragment() {
    private lateinit var binding: FragmentExerciseListBinding
    private lateinit var viewModel: ExerciseListViewModel
    private lateinit var exerciseListAdapter: ExerciseListAdapter
    val args: ExerciseListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        val WorkoutPlansDao = WorkoutPlansDatabase.getDatabase(requireContext()).WorkoutPlansDao()
        val exerciseRepository = ExerciseRepository(WorkoutPlansDao, args.workoutPlanID)
        viewModel = ViewModelProvider(this, ExerciseListViewModelFactory(exerciseRepository))[ExerciseListViewModel::class.java]
        //viewModel.getPlanExercise(args.workoutPlanID)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.workoutPlanTitle.text = args.workoutPlanTitle
        viewModel.exerciseItems.observe(this){
            exerciseList -> exerciseListAdapter.submitList(exerciseList)
        }
        setupRecycleView()
        setupButtons()
        return binding.root
    }

    fun setupButtons(){
        binding.editButton.setOnClickListener{
            Snackbar.make(requireView(), "Edycja planu", Snackbar.LENGTH_LONG).show()
        }
        binding.saveButton.setOnClickListener{
            Snackbar.make(requireView(), "Zapisanie planu", Snackbar.LENGTH_LONG).show()
        }
        binding.addExerciseButton.setOnClickListener{
            val actionExerciseListToAddExerciseFragment = ExerciseListFragmentDirections.actionExerciseListFragmentToAddExerciseFragment(args.workoutPlanID)
            findNavController().navigate(actionExerciseListToAddExerciseFragment)
        }
    }

    private fun setupRecycleView() {
        // sharedViewModel = ViewModelProvider(this, DeleteDialogViewModelFactory(workoutPlansRepository))[DeleteDialogViewModel::class.java]
        exerciseListAdapter = ExerciseListAdapter(
            object: ExerciseListListener {
                override fun onItemClick(exercise: Exercise) {
                    //displayWorkoutPlan(workoutPlan)
                    Snackbar.make(requireView(), "NO SIEMA ${exercise.name}", Snackbar.LENGTH_LONG).show()
                }

                override fun onItemLongClick(exercise: Exercise) {
                    Snackbar.make(requireView(), "Delete ${exercise.name}?", Snackbar.LENGTH_LONG).show()
                    //showDeleteDialog(workoutPlan)
                    //sharedViewModel.cos(view!!)
                    //viewModel.deleteWorkoutPlan(workoutPlan)
                }

                override fun updateButtonClick(exercise: Exercise) {
                    viewModel.updateExercise(exercise)
                }
            })
        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exerciseListRecyclerView.adapter = exerciseListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //viewModel.deleteALl()
    }
}