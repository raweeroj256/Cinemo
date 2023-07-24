package je.raweeroj.cinemo.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class],version = 1)
abstract class MovieDatabase: RoomDatabase() {


    abstract fun movieDao() : MovieDAO


    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null


        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {

                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }


                return instance
            }
        }
    }

}