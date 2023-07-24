package je.raweeroj.cinemo.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("Select * from `movie-table`")
    fun fetchAllMovie(): Flow<List<MovieEntity>>

    @Query("Select * from `movie-table` where id=:id")
    fun fetchMovieById(id: Int):Flow<List<MovieEntity>>


}