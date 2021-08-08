/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.network.api

import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MoviesResponse
import com.gmail.hostov47.androidschoolmvvm.domain.api.ImdbApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class OkHttpApi: ImdbApi {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    override fun getUpcomingMovies(): Single<MoviesResponse> {
        val request = Request.Builder()
            .url("${BASE_URL}movie/upcoming?api_key=${API_KEY}".toHttpUrl())
            .build()

        return handleResponse<MoviesResponse>(request)
    }

    override fun getNowPlayingMovies(): Single<MoviesResponse> {
        val request = Request.Builder()
            .url("${BASE_URL}movie/now_playing?api_key=${API_KEY}".toHttpUrl())
            .build()

        return handleResponse<MoviesResponse>(request)
    }

    override fun getPopularMovies(): Single<MoviesResponse> {
        val request = Request.Builder()
            .url("${BASE_URL}movie/popular?api_key=${API_KEY}".toHttpUrl())
            .build()

        return handleResponse<MoviesResponse>(request)
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetailResponse> {
        val request = Request.Builder()
            .url("${BASE_URL}movie/$movieId?api_key=${API_KEY}".toHttpUrl())
            .build()

        return handleResponse<MovieDetailResponse>(request)
    }

    override fun getMovieCredits(movieId: Int): Single<MovieCreditsResponse> {
        val request = Request.Builder()
            .url("${BASE_URL}movie/$movieId/credits?api_key=${API_KEY}".toHttpUrl())
            .build()
        return handleResponse<MovieCreditsResponse>(request)
    }

    private inline fun <reified T> handleResponse(request: Request): Single<T> {
        var moviesResponse: T? = null
        return Single.defer {
            try {
                val response = okHttpClient.newCall(request).execute()
                val string = response.body?.string() ?: "No content"
                moviesResponse = getMovieResponse<T>(string)
                return@defer Single.just(moviesResponse!!)
            }catch (e: IOException){
                return@defer Single.just(moviesResponse!!)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private inline fun <reified T> getMovieResponse(rawJson: String): T?{
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return adapter.fromJson(rawJson)
    }


}