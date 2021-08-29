/**
 * Created by Ilia Shelkovenko on 29.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.profile

import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.WatchListDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.WatchListMovie
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.models.presentation.toWatchListMovie
import io.reactivex.Single
import javax.inject.Inject

class WatchListRepositoryImpl @Inject constructor(
    private val watchListDao: WatchListDao
) : WatchListRepository {

    override fun isInWatchList(movieId: Int): Single<Boolean> {
        return watchListDao.isInWatchList(movieId).map { it != null }
    }

    override fun addToWatchList(movie: MovieDetailsWithCast) {
        watchListDao.insert(movie.toWatchListMovie())
    }

    override fun removeFromWatchList(movie: MovieDetailsWithCast) {
        watchListDao.delete(movieId = movie.id)
    }

    override fun getWatchList(): Single<List<MoviePreview>> {
        return watchListDao.getWatchList().map { it.toMoviePreviewList() }
    }
}

private fun List<WatchListMovie>.toMoviePreviewList(): List<MoviePreview>? {
    return this.map { MoviePreview(it.movieId, it.title, it.poster) }
}