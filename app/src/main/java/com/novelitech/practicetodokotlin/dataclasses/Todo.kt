package com.novelitech.practicetodokotlin.dataclasses

import java.util.UUID

data class Todo (
    val id: UUID,
    val title: String,
    val done: Boolean,

    @Transient
    val onRemove: () -> Unit,

    @Transient
    val onCheck: (Boolean) -> Unit
)