package xyz.manolos.inmovies.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.manolos.inmovies.model.ResponseGenres
import xyz.manolos.inmovies.model.ResponseMovies

interface MovieService {

//    @GET("movie/upcoming")
//    fun fetchMovies(@Query("api_key") api_key: String, @Query("page") page: Int): Single<ResponseMovies>
//
//    @GET("genre/movie/list")
//    fun fetchGenres(@Query("api_key") api_key: String): Single<ResponseGenres>

    @GET("movie/upcoming")
    fun fetchMovies(@Query("page") page: Int): Single<ResponseMovies>

    @GET("genre/movie/list")
    fun fetchGenres(): Single<ResponseGenres>
}