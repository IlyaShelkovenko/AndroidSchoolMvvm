package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.FavoriteMoviesRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.WatchListRepository
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieCastDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject


/**
 * Интерактор для взаимодействия с детальной информацией о фильме
 *
 * @author Shelkovenko Ilya on 2021-08-04
 */
class MovieDetailsInteractor @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    private val watchListRepository: WatchListRepository,
) {

    /**
     * Метод, получающий детальную информацию о фильме.
     *
     * @param movieId идентификатор фильма.
     * @param forceLoad флаг для получения данных с сети.
     * @return ответ с детальной информацией о фильме [MovieDetailsDomain]
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getMovieDetails(movieId: Int, forceLoad: Boolean): MovieDetailsDomain {
        val localDetails = detailsRepository.getMovieDetails(movieId, forceLoad)
        return MovieDetailsDomain(
            localDetails.id,
            localDetails.posterPath,
            localDetails.title,
            localDetails.voteAverage,
            localDetails.overview,
            localDetails.productionCompanies,
            localDetails.genres,
            localDetails.releaseDate,
        )
    }

    /**
     * Метод, получающий информацию о команде фильма.
     *
     * @param movieId идентификатор фильма.
     * @param forceLoad флаг для получения данных с сети.
     * @return список каста фильма [MovieCastDomain]
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getMovieCast(movieId: Int, forceLoad: Boolean): List<MovieCastDomain> {
        return detailsRepository.getMovieCredits(movieId, forceLoad).map {
            MovieCastDomain(
                it.id,
                it.name,
                it.profilePath,
            )
        }
    }

    /**
     * Метод, добавляющий фильм в список понравившихся хрянищийся в базе данных.
     *
     * @param movie [MovieDetailsWithCast] фильм, который хотим добавить в базу данных понравившихся фильмов.
     */
    fun addToFavorite(movie: MovieDetailsWithCast) {
        favoriteMoviesRepository.addToFavorite(movie)
    }

    /**
     * Метод, удаляющий фильм из списка понравившихся в базе данных.
     *
     * @param movie [MovieDetailsWithCast] фильм, который хотим удалить из базы данных понравившихся фильмов.
     */
    fun removeFromFavorite(movie: MovieDetailsWithCast) {
        favoriteMoviesRepository.removeFromFavorite(movie)
    }

    /**
     * Метод, проверяющий, есть ли фильм в базе данных понравившихся фильмов.
     *
     * @param movie [MovieDetailsWithCast] фильм, который хотим проверить на наличие в базе данных понравившихся фильмов.
     */
    fun isMovieFavorite(movie: MovieDetailsWithCast): Single<Boolean> {
        return favoriteMoviesRepository.isFavorite(movieId = movie.id)
    }

    /**
     * Метод, добавляющий фильм в список фильмов к просмотру хрянищийся в базе данных.
     *
     * @param movie [MovieDetailsWithCast] фильм, который хотим добавить в базу данных фильмов к просмотру.
     */
    fun addToWatchList(movie: MovieDetailsWithCast) {
        watchListRepository.addToWatchList(movie)
    }

    /**
     * Метод, удаляющий фильм из списка понравившихся в базе данных.
     *
     * @param movie [MovieDetailsWithCast] фильм, который хотим удалить из базы данных фильмов к просмотру.
     */
    fun removeFromWatchWist(movie: MovieDetailsWithCast) {
        watchListRepository.removeFromWatchList(movie)
    }

    /**
     * Метод, проверяющий, есть ли фильм в базе данных фильмов к просмотру.
     *
     * @param movie [MovieDetailsWithCast] фильм, который хотим проверить на наличие в базе данных фильмов к просмотру.
     */
    fun isMovieInWatchList(movie: MovieDetailsWithCast): Single<Boolean> {
        return watchListRepository.isInWatchList(movieId = movie.id)
    }
}