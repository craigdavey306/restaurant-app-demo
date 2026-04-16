package com.cdavey.restaurantsapp.restaurants.data.local

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE restaurants ADD COLUMN is_favorite INTEGER NOT NULL DEFAULT ''")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // do nothing
    }
}

@Database(entities = [LocalRestaurant::class], version = 3, exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantDao
}