/**
 * Created by Ilia Shelkovenko on 12.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

class FromMovieLocalToMovieDomainMapperTest {
    private lateinit var localToDomainMapper: FromMovieLocalToMovieDomainMapper
    private var localMovies: List<MovieLocal>? = null
    private lateinit var moviesDomain: List<MovieDomain>

    @Before
    fun setUp(){
        localToDomainMapper = FromMovieLocalToMovieDomainMapper
        localMovies = moviesLocal
        moviesDomain = localToDomainMapper.mapList(localMovies)
    }

    @Test
    fun `check correct items`(){
        for(index in response.results!!.indices){
            checkMovieItem(moviesDomain[index], moviesLocal[index])
        }
    }

    private fun checkMovieItem(movieDomain: MovieDomain, movieLocal: MovieLocal) {
        MatcherAssert.assertThat(movieDomain.isAdult, CoreMatchers.`is`(movieLocal?.isAdult))
        MatcherAssert.assertThat(movieDomain.overview, CoreMatchers.`is`(movieLocal?.overview))
        MatcherAssert.assertThat(movieDomain.releaseDate, CoreMatchers.`is`(movieLocal?.releaseDate))
        MatcherAssert.assertThat(movieDomain.genreIds, CoreMatchers.`is`(movieLocal?.genreIds))
        MatcherAssert.assertThat(movieDomain.id, CoreMatchers.`is`(movieLocal?.movieId))
        MatcherAssert.assertThat(movieDomain.originalTitle, CoreMatchers.`is`(movieLocal?.originalTitle))
        MatcherAssert.assertThat(movieDomain.originalLanguage, CoreMatchers.`is`(movieLocal?.originalLanguage))
        MatcherAssert.assertThat(movieDomain.title, CoreMatchers.`is`(movieLocal?.title))
        MatcherAssert.assertThat(movieDomain.backdropPath, CoreMatchers.`is`(movieLocal?.backdropPath))
        MatcherAssert.assertThat(movieDomain.popularity, CoreMatchers.`is`(movieLocal?.popularity))
        MatcherAssert.assertThat(movieDomain.voteCount, CoreMatchers.`is`(movieLocal?.voteCount))
        MatcherAssert.assertThat(movieDomain.video, CoreMatchers.`is`(movieLocal?.video))
        MatcherAssert.assertThat(movieDomain.rating, CoreMatchers.`is`((movieLocal.voteAverage?.toFloat() ?: 0f) / 2))
        MatcherAssert.assertThat(movieDomain.posterPath, CoreMatchers.`is`(movieLocal.posterPath?.let{ "${ BuildConfig.POSTER_PATH}$it" }))
    }

    @Test
    fun `check null results`(){
        localMovies = null
        moviesDomain = FromMovieLocalToMovieDomainMapper.mapList(localMovies)
        Assert.assertArrayEquals(emptyArray(), moviesDomain.toTypedArray());
    }

    @Test
    fun `check empty list results`(){
        localMovies = emptyList()
        moviesDomain = FromMovieLocalToMovieDomainMapper.mapList(localMovies)
        Assert.assertArrayEquals(emptyArray(), moviesDomain.toTypedArray());
    }
}