package com.novelitech.practicetodokotlin.repositories.todo_repository

import com.novelitech.practicetodokotlin.dataclasses.Todo

interface ITodoRepository {
    
    val KEY_TODOS: String
        get() = "KEY_TODOS"

    fun save(items: List<Todo>)

    fun getAll() : List<Todo>
}