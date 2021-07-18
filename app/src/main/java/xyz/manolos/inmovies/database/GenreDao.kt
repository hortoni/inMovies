package xyz.manolos.inmovies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import xyz.manolos.inmovies.model.Genre

@Dao
interface GenreDao {

    @Query("select * from genre")
    fun getAllGenres(): LiveData<List<Genre>>

    @Insert(onConflict = REPLACE)
    fun insertGenres(genres: List<Genre>): Completable

}