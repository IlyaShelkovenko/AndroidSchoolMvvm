/**
 * Created by Ilia Shelkovenko on 15.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieDetailResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FromDetailsResponseToDetailsLocalTest {
    private lateinit var responseToDetailsMapper: FromDetailsResponseToDetailsLocal
    private var response: MovieDetailResponse? = null
    private lateinit var movieLocal: MovieDetailsLocal

    @Before
    fun setUp(){
        responseToDetailsMapper = FromDetailsResponseToDetailsLocal
        response = getMovieDetails()
    }

    @Test
    fun responseToLocalTest(){
        val actualDetailsLocal = responseToDetailsMapper.map(response!!)

        checkItemEquals(actualDetailsLocal, response!!)
    }

    private fun checkItemEquals(actualDetailsLocal: MovieDetailsLocal, response: MovieDetailResponse) {
        val productionCompanies =
            if (response!!.productionCompanies == null || response!!.productionCompanies!!.isEmpty()) "-"
            else response.productionCompanies!!.joinToString(", ") { it.name }
        val genres = if (response.genres == null || response.genres!!.isEmpty()) "-"
        else response.genres!!.joinToString(", ") { it.name }
        MatcherAssert.assertThat(actualDetailsLocal.movieId, CoreMatchers.`is`(response.id))
        MatcherAssert.assertThat(actualDetailsLocal.posterPath, CoreMatchers.`is`(response.posterPath))
        MatcherAssert.assertThat(actualDetailsLocal.title, CoreMatchers.`is`(response.title))
        MatcherAssert.assertThat(actualDetailsLocal.voteAverage, CoreMatchers.`is`(response.voteAverage))
        MatcherAssert.assertThat(actualDetailsLocal.overview, CoreMatchers.`is`(response.overview))
        MatcherAssert.assertThat(actualDetailsLocal.productionCompanies, CoreMatchers.`is`(productionCompanies))
        MatcherAssert.assertThat(actualDetailsLocal.genres, CoreMatchers.`is`(genres))
        MatcherAssert.assertThat(actualDetailsLocal.releaseDate, CoreMatchers.`is`(response.releaseDate))

    }
}