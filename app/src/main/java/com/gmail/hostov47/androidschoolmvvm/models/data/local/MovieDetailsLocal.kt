/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.data.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsLocal(
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val title: String,
    @SerialName("vote_average")
    val voteAverage: Double?,
    val overview: String? = null,
    @SerialName("production_countries")
    val productionCompanies: String,
    val genres: String,
    @SerialName("release_date")
    val releaseDate: String
)
