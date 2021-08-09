package ru.techcrat.test.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Area::class, LocalPoint::class],
    version = 4,
    exportSchema = true
)
abstract class AreasDatabase : RoomDatabase() {

    abstract fun areaDao(): AreaDao

    companion object {



        @Volatile
        private var INSTANCE: AreasDatabase? = null

        fun getDatabase(context: Context): AreasDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AreasDatabase::class.java,
                    "area_table"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}