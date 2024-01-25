package com.novelitech.practicetodokotlin.repositories.todo_repository

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.novelitech.practicetodokotlin.dataclasses.Todo
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.novelitech.practicetodokotlin.databinding.ActivityMainBinding

class TodoRepository(
    private val activity: AppCompatActivity,
    private val binding: ActivityMainBinding
) : ITodoRepository {

    override fun save(items: List<Todo>) {

        try {
            val sharedPreference = activity.getSharedPreferences("TODO_APP_PRACTICE", Context.MODE_PRIVATE)

            val editor = sharedPreference.edit()

            val gson = Gson()

            val listJson = gson.toJson(items)

            editor.apply {
                putString(KEY_TODOS, listJson)
                apply()
            }
        } catch (e: Exception) {

            val errorMessage = "Error saving the data"

            showSnackbarError(errorMessage)

            Log.d("REPOSITORY", errorMessage)
        }
    }

    override fun getAll(): List<Todo> {

        try {

            val sharedPreference = activity.getSharedPreferences("TODO_APP_PRACTICE", Context.MODE_PRIVATE)

            val jsonList = sharedPreference.getString(KEY_TODOS, null)

            if(jsonList == null || jsonList!!.isEmpty()) {
                return listOf()
            }

            val gson = Gson()

            val listTodoType = object : TypeToken<List<Todo>>() {}.type

            return gson.fromJson(jsonList, listTodoType)

        } catch(e: Exception) {
            val errorMessage = "Error fetching the data"

            showSnackbarError(errorMessage)

            Log.d("REPOSITORY", errorMessage)
        }

        return listOf()
    }

    private fun showSnackbarError(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

        snackbar.setBackgroundTint(Color.RED)
        snackbar.setTextColor(Color.WHITE)

        snackbar.setAction("OK") {
            snackbar.dismiss()
        }

        snackbar.setActionTextColor(Color.WHITE)

        snackbar.show()
    }
}