package com.example.myfavoritequotes.base.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myfavoritequotes.base.module.Quotes

@Dao
interface QuotesDao {

    @Query("Select * from Quotes")
    suspend fun getAllQuotes(): List<Quotes>

    @Insert
    suspend fun insert(quote: Quotes)

    @Query("Update Quotes set favorite = 1 where id = :id")
    suspend fun updateNewFavorite(id: Int)

    @Query("Update Quotes set favorite = 0 where id = :id")
    suspend fun unsetFavorite(id: Int)

    @Query("Select * from Quotes where id = :id")
    suspend fun getFavoriteQuote(id: Int): Quotes

    @Query("Delete from Quotes")
    suspend fun deleteAll()

    @Query("Select favorite from Quotes where id = :id")
    suspend fun isFavorite(id: Int): Boolean
}