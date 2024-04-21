package com.example.workoutapp.main_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        setButtonListeners()
        return binding.root
    }

    private fun setButtonListeners(){
        binding.searchForExercises.setOnClickListener{searchForExercisesButtonClick()}
        binding.workoutPlansLists.setOnClickListener{yourWorkoutsButtonClick()}
    }

    private fun searchForExercisesButtonClick(){
        findNavController().navigate(R.id.action_mainMenuFragment_to_searchExercisesFragment)
    }

    private fun yourWorkoutsButtonClick(){
        findNavController().navigate(R.id.action_mainMenuFragment_to_yourWorkoutsFragment)
    }

}