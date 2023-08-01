package com.valijonboy.data.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String,
    val lastName: String? = null,
    val phone: String? = null,
)
