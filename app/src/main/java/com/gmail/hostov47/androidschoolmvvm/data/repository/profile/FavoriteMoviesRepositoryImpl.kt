/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.profile

import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.FavoriteMoviesDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.FavoriteMovie
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.models.presentation.toFavoriteMovie
import io.reactivex.Single
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteMoviesDao
) : FavoriteMoviesRepository {

    override fun isFavorite(movieId: Int): Single<Boolean> {
        return favoriteDao.isFavorite(movieId).map { it != null }
    }

    override fun addToFavorite(movie: MovieDetailsWithCast) {
        favoriteDao.insert(movie.toFavoriteMovie())
    }

    override fun removeFromFavorite(movie: MovieDetailsWithCast) =
        favoriteDao.delete(movie.id)

    override fun getFavoriteMovies(): Single<List<MoviePreview>> {
        return favoriteDao.getFavoriteMovies().map { it.toMoviePreviewList() }
    }
}

private fun List<FavoriteMovie>.toMoviePreviewList(): List<MoviePreview>? {
    return this.map { MoviePreview(it.movieId, it.title, it.poster) }
}