package com.cdavey.restaurantsapp

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

@Database(entities = [Restaurant::class], version = 2, exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantsDb? = null // Store the DB class

        fun getDaoInstance(context: Context): RestaurantDao {
            return (INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }).dao
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context.applicationContext,
                RestaurantsDb::class.java,
                "restaurants_database"
            )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}