package com.novelitech.practicetodokotlin.repositories.todo_repository

import android.content.Context
import com.novelitech.practicetodokotlin.dataclasses.Todo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.hardware.display.DisplayManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoRepository(private val activity: AppCompatActivity) : ITodoRepository {

    override fun save(items: List<Todo>) {

        val sharedPreference = activity.getSharedPreferences("TODO_APP_PRACTICE", Context.MODE_PRIVATE)

        val editor = sharedPreference.edit()

        val gson = Gson()

        val listJson = gson.toJson(items)

        editor.apply {
            putString(KEY_TODOS, listJson)
            apply()
        }

        Log.d("TODO", listJson)
    }

    override fun getAll(): List<Todo> {
        val sharedPreference = activity.getSharedPreferences("TODO_APP_PRACTICE", Context.MODE_PRIVATE)

        val jsonList = sharedPreference.getString(KEY_TODOS, null)

        if(jsonList == null || jsonList!!.isEmpty()) {
            return listOf()
        }

        val gson = Gson()

        val listTodoType = object : TypeToken<List<Todo>>() {}.type

        return gson.fromJson(jsonList, listTodoType)
    }
}