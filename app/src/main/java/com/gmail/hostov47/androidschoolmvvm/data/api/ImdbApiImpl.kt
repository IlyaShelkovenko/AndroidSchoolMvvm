package com.gmail.hostov47.androidschoolmvvm.data.api

import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MoviesResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject


/**
 * Реализация [ImdbApi]
 *
 * @param okHttpClient клиент для работы с сетью
 * @param json сериализатор из Json в модельки
 */
class ImdbApiImpl @Inject constructor (private val okHttpClient: OkHttpClient, private val json: Json) :
    ImdbApi {

    override fun getPopularMovies(): MoviesResponse {
        val request = createGetRequest("${BASE_URL}movie/popular?api_key=${API_KEY}&language=ru")
        return okHttpClient.newCall(request).execute().use { response ->
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    override fun getUpcomingMovies(): MoviesResponse {
        val request = createGetRequest("${BASE_URL}movie/upcoming?api_key=${API_KEY}&language=ru")
        return okHttpClient.newCall(request).execute().use { response ->
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    override fun getNowPlayingMovies(): MoviesResponse {
        val request = createGetRequest("${BASE_URL}movie/now_playing?api_key=${API_KEY}&language=ru")
        return okHttpClient.newCall(request).execute().use { response ->
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    override fun getMovieDetails(movieId: Int): MovieDetailResponse {
        val request = createGetRequest("${BASE_URL}movie/$movieId?api_key=${API_KEY}&language=ru")
        return okHttpClient.newCall(request).execute().use { response ->
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    override fun getMovieCredits(movieId: Int): MovieCreditsResponse {
        val request = createGetRequest("${BASE_URL}movie/$movieId/credits?api_key=${API_KEY}&language=ru")
        return okHttpClient.newCall(request).execute().use { response ->
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    private fun createGetRequest(url: String): Request =
        Request.Builder()
            .url(url)
            .get()
            .build()
}