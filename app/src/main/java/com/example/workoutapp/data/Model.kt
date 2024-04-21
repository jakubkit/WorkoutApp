package com.example.workoutapp.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import java.io.File
import java.io.FileWriter
import java.io.Serializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object WorkoutPlans: Serializable {
    lateinit var ITEMS: MutableList<WorkoutPlan>
    private var plansFile: String = "workoutPlans.json"

    fun addWorkoutPlan(plan: WorkoutPlan){
        ITEMS.add(plan)
    }

    fun updateWorkoutPlan(planToEdit: WorkoutPlan?, newPlan: WorkoutPlan){
        planToEdit?.let {
            oldPlan ->
            //perform operation only when planToEdit is !null
            //find idx of old plan
            val idxOfOldPlan = ITEMS.indexOf(oldPlan)
            ITEMS.add(idxOfOldPlan,newPlan)
            ITEMS.removeAt(idxOfOldPlan+1)
        }
    }

    fun deleteWorkoutPlan(pos: Int?){
        ITEMS.removeAt(pos!!)
    }

    fun savePlansToFile(context: Context){
        val file = File(context.cacheDir, plansFile)
        val gson = Gson()
        val toJson = gson.toJson(ITEMS)
        val writer = FileWriter(file)
    }

    fun restoreGroceryListsFromFile(context: Context) {
        try {
            val file = File(context.cacheDir,plansFile)
            val gson = Gson()
            val listOfMyClassObject: Type = object : TypeToken<MutableList<WorkoutPlan>>() {}.type
            val fromJson: MutableList<WorkoutPlan> = gson.fromJson(file.readText(), listOfMyClassObject)
            ITEMS.clear()
            ITEMS.addAll(fromJson)
        }
        catch (e: Exception) {
            ITEMS.clear()
            val exampleExercise = Exercise("example", 10, 3, 2.5F, ArrayList())
            val temp: MutableList<Exercise> = ArrayList()
            temp.add(exampleExercise)
            ITEMS.add(WorkoutPlan(1, "List 1", temp))
        }
    }
}

data class WorkoutPlan(
    val id: Int, val title: String, var exerciseList: MutableList<Exercise>
):Parcelable{

    fun addExercise(exercise: Exercise){
        exerciseList.add(exercise)
    }

    fun updateExercise(exerciseToEdit: Exercise?, newExercise: Exercise){
        exerciseToEdit?.let {
                oldExercise ->
            //perform operation only when exerciseToEdit is !null
            //find idx of old exercise
            val idxOfOldExercise = exerciseList.indexOf(oldExercise)
            exerciseList.add(idxOfOldExercise,newExercise)
            exerciseList.removeAt(idxOfOldExercise+1)
        }
    }

    fun deleteExercise(pos: Int?){
        exerciseList.removeAt(pos!!)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readString()!!,
        mutableListOf<Exercise>().apply { parcel.readTypedList(this, Exercise.CREATOR) }!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeTypedList(exerciseList)
    }

    companion object CREATOR : Parcelable.Creator<WorkoutPlan> {
        override fun createFromParcel(parcel: Parcel): WorkoutPlan {
            return WorkoutPlan(parcel)
        }

        override fun newArray(size: Int): Array<WorkoutPlan?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}

data class Exercise(
    val exerciseName: String, val repetitions: Int,
    val sets: Int, val weight: Float, var bodyParts: MutableList<String>
): Parcelable {

    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readFloat()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int){
        parcel.writeString(exerciseName)
        parcel.writeInt(repetitions)
        parcel.writeInt(sets)
        parcel.writeFloat(weight)
        parcel.writeStringList(bodyParts)
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}

