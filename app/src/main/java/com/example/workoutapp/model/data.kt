package com.example.workoutapp.model

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Junction
import androidx.room.Transaction
import androidx.room.Update

class WorkoutPlansRepository(private val workoutPlansDao: WorkoutPlansDao){

    val workoutPlansItems: LiveData<List<WorkoutPlan>> = workoutPlansDao.getAllPlans()

    suspend fun insertWorkoutPlan(workoutPlan: WorkoutPlan){
        workoutPlansDao.insertWorkoutPlan(workoutPlan)
    }
}

class ExerciseRepository(private val workoutPlansDao: WorkoutPlansDao, planId: Int){
    val exerciseListItems: LiveData<MutableList<Exercise>> = workoutPlansDao.getPlanExercises(planId)

    suspend fun insertExercise(exercise: Exercise){
        workoutPlansDao.insertExercise(exercise)
    }

    suspend fun updateExercise(exercise: Exercise){
        workoutPlansDao.updateExercise(exercise.exerciseId, exercise.repetitions, exercise.sets, exercise.weight, exercise.notes)
//        workoutPlansDao.updateExercise(exercise)
    }

}
@Dao
interface WorkoutPlansDao {
    @Insert
    suspend fun insertWorkoutPlan(workoutPlan: WorkoutPlan)

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise")
    fun getAllExercise(): LiveData<List<Exercise>>

    @Query("SELECT * FROM workoutplan")
    fun getAllPlans(): LiveData<List<WorkoutPlan>>

    @Query("SELECT * FROM exercise WHERE workoutPlanID = :planId")
    fun getPlanExercises(planId: Int): LiveData<MutableList<Exercise>>

    @Query("UPDATE exercise SET repetitions = :repetitions, sets = :sets, weight = :weight, notes = :notes WHERE exerciseId = :exerciseId")
    suspend fun updateExercise(exerciseId: Int, repetitions: Int, sets: Int, weight: Double, notes: String)
//    @Update
//    suspend fun updateExercise(exercise: Exercise)
}

@Entity
data class WorkoutPlan(
    val title: String,
    @PrimaryKey(autoGenerate = true)
    var planId: Int = 0){}

@Entity
data class Exercise(
    var name: String,
    var workoutPlanID: Int,
    var sets: Int,
    var repetitions: Int,
    var weight: Double,
    var notes: String,
    @PrimaryKey(autoGenerate = true)
    var exerciseId: Int = 0){}
@Database(entities = [WorkoutPlan::class, Exercise::class], version = 2, exportSchema = false)
abstract class WorkoutPlansDatabase:RoomDatabase(){
    abstract fun WorkoutPlansDao(): WorkoutPlansDao
    companion object{
        @Volatile
        private var INSTANCE: WorkoutPlansDatabase? = null
        fun getDatabase(context: Context): WorkoutPlansDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutPlansDatabase::class.java,
                    "workoutplans_exercises_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

