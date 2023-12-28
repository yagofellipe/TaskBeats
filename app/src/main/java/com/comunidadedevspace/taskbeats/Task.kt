package com.comunidadedevspace.taskbeats

import java.io.Serializable

data class Task(
    val id: Int,
    var title: String,
    var description: String
): Serializable
