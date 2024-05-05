package com.example.workoutapp.workout_plans_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.model.WorkoutPlan
import com.example.workoutapp.databinding.FragmentYourWorkoutsBinding
import com.example.workoutapp.model.WorkoutPlansDatabase
import com.example.workoutapp.model.WorkoutPlansRepository
import com.google.android.material.snackbar.Snackbar

class YourWorkoutsFragment : Fragment(){
    private lateinit var viewModel: YourWorkoutsViewModel
    private lateinit var binding: FragmentYourWorkoutsBinding
    private lateinit var workoutPlansAdapter: WorkoutPlansAdapter
    //private lateinit var sharedViewModel: DeleteDialogViewModel
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYourWorkoutsBinding.inflate(layoutInflater, container, false)
        val workoutPlanDao = WorkoutPlansDatabase.getDatabase(requireContext()).WorkoutPlansDao()
        val workoutPlansRepository = WorkoutPlansRepository(workoutPlanDao)
        viewModel = ViewModelProvider(this, YourWorkoutsViewModelFactory(workoutPlansRepository))[YourWorkoutsViewModel::class.java]
        binding.viewModel = viewModel
        //binding.lifecycleOwner = this
        setupRecycleView()
        viewModel.allWorkoutPlans.observe(this){
            exerciseList -> workoutPlansAdapter.submitList(exerciseList)
        }
        binding.addButton.setOnClickListener{addButtonClick()}
        return binding.root
    }

    private fun addButtonClick() {
        val actionYourWorkoutsToAddWorkoutPlanFragment = YourWorkoutsFragmentDirections.actionYourWorkoutsFragmentToAddWorkoutPlanFragment()
        findNavController().navigate(actionYourWorkoutsToAddWorkoutPlanFragment)
    }

//    private fun showDeleteDialog(workoutPlan: WorkoutPlan){
//        Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG).show()
//    }

    private fun setupRecycleView() {
       // sharedViewModel = ViewModelProvider(this, DeleteDialogViewModelFactory(workoutPlansRepository))[DeleteDialogViewModel::class.java]
        workoutPlansAdapter = WorkoutPlansAdapter(
            object: WorkoutPlansListener{
                override fun onItemClick(workoutPlan: WorkoutPlan) {
                    val actionYourWorkoutsListToExerciseList = YourWorkoutsFragmentDirections.actionYourWorkoutsFragmentToExerciseListFragment(workoutPlan.planId, workoutPlan.title)
                    findNavController().navigate(actionYourWorkoutsListToExerciseList)
                }

                override fun onItemLongClick(workoutPlan: WorkoutPlan) {
                    Snackbar.make(requireView(), "Delete ${workoutPlan.title}?", Snackbar.LENGTH_LONG).show()
                    //showDeleteDialog(workoutPlan)
                    //sharedViewModel.cos(view!!)
                    //viewModel.deleteWorkoutPlan(workoutPlan)
                }
            })
        binding.workoutsListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutsListRecyclerView.adapter = workoutPlansAdapter
    }
}
