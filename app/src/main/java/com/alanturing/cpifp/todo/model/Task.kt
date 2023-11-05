package com.alanturing.cpifp.todo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    var id:Int,

    val title: String?,

    val description: String?,

    val isCompleted: Boolean = false
): Parcelable