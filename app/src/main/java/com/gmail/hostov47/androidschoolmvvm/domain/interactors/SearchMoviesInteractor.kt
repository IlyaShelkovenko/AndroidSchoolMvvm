/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromMovieLocalToMovieDomainMapper
import com.gmail.hostov47.androidschoolmvvm.data.repository.search.SearchRepository
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.SearchMovieResponse
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchMoviesInteractor @Inject constructor(private val repository: SearchRepository) {
    fun searchMovies(query: String): List<MovieDomain> {
        return repository.searchMovies(query)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }
    }
}