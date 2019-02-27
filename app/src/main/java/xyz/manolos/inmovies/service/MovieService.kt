package xyz.manolos.inmovies.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.manolos.inmovies.model.Response

interface MovieService {

    @GET("movie/upcoming")
    fun fetch(@Query("api_key") api_key: String, @Query("page") page: Int): Single<Response>
}