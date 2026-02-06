package com.example.myfavoritequotes.base.module

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Quotes")
class Quotes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var quote: String = " ",
    var author: String = " ",
    var favorite: Boolean = false
) {}