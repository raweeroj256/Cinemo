package je.raweeroj.cinemo.network

import je.raweeroj.cinemo.models.MovieResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface MovieService {

    @GET("get_movie_avaiable")
    fun getMovie(): Call<MovieResponse>
}