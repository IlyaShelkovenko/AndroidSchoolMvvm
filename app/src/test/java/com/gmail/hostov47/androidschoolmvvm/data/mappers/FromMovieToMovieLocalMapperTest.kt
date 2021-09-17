/**
 * Created by Ilia Shelkovenko on 11.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.Movie
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MoviesResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FromMovieToMovieLocalMapperTest {
    private lateinit var movieToLocalMapper: FromMovieToMovieLocalMapper
    private lateinit var response: MoviesResponse
    private lateinit var moviesLocal: List<MovieLocal>
    private var results: List<Movie>? = null

    @Before
    fun setUp(){
        movieToLocalMapper = FromMovieToMovieLocalMapper
        response = getMoviesResponse()
        results = response.results
        moviesLocal = movieToLocalMapper.mapList(results!!)
    }

    @Test
    fun `check correct items`(){
        for(index in response.results!!.indices){
            checkMovieItem(moviesLocal[index], results?.get(index))
        }
    }

    private fun checkMovieItem(movieLocal: MovieLocal, movieRes: Movie?) {
        MatcherAssert.assertThat(movieLocal.isAdult,CoreMatchers.`is`(movieRes?.isAdult))
        MatcherAssert.assertThat(movieLocal.overview,CoreMatchers.`is`(movieRes?.overview))
        MatcherAssert.assertThat(movieLocal.releaseDate,CoreMatchers.`is`(movieRes?.releaseDate))
        MatcherAssert.assertThat(movieLocal.genreIds,CoreMatchers.`is`(movieRes?.genreIds))
        MatcherAssert.assertThat(movieLocal.movieId,CoreMatchers.`is`(movieRes?.id))
        MatcherAssert.assertThat(movieLocal.originalTitle,CoreMatchers.`is`(movieRes?.originalTitle))
        MatcherAssert.assertThat(movieLocal.originalLanguage,CoreMatchers.`is`(movieRes?.originalLanguage))
        MatcherAssert.assertThat(movieLocal.title,CoreMatchers.`is`(movieRes?.title))
        MatcherAssert.assertThat(movieLocal.backdropPath,CoreMatchers.`is`(movieRes?.backdropPath))
        MatcherAssert.assertThat(movieLocal.popularity,CoreMatchers.`is`(movieRes?.popularity))
        MatcherAssert.assertThat(movieLocal.voteCount,CoreMatchers.`is`(movieRes?.voteCount))
        MatcherAssert.assertThat(movieLocal.video,CoreMatchers.`is`(movieRes?.video))
        MatcherAssert.assertThat(movieLocal.voteAverage,CoreMatchers.`is`(movieRes?.voteAverage))
        MatcherAssert.assertThat(movieLocal.posterPath,CoreMatchers.`is`(movieRes?.posterPath))
    }

    @Test
    fun `check null results`(){
        response.results = null
        moviesLocal = FromMovieToMovieLocalMapper.mapList(response.results)
        Assert.assertArrayEquals(emptyArray(), moviesLocal.toTypedArray());
    }

    @Test
    fun `check empty list results`(){
        response.results = emptyList()
        moviesLocal = FromMovieToMovieLocalMapper.mapList(response.results)
        Assert.assertArrayEquals(emptyArray(), moviesLocal.toTypedArray());
    }
}