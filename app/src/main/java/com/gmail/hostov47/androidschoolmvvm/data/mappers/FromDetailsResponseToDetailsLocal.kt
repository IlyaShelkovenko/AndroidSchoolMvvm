package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieDetailResponse

object FromDetailsResponseToDetailsLocal {
    fun map(response: MovieDetailResponse): MovieDetailsLocal {
        val productionCompanies =
            if (response.productionCompanies == null || response.productionCompanies.isEmpty()) "-"
            else response.productionCompanies.joinToString(", ") { it.name }
        val genres = if (response.genres == null || response.genres.isEmpty()) "-"
        else response.genres.joinToString(", ") { it.name }
        return MovieDetailsLocal(
            response.id,
            response.posterPath,
            response.title,
            response.voteAverage,
            response.overview,
            productionCompanies,
            genres,
            response.releaseDate,
        )
    }
}