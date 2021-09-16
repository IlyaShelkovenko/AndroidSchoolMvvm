/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.domain

data class MovieDetailsDomain(
    val id: Int,
    val posterPath: String? = null,
    val title: String,
    val rating: Float,
    val overview: String? = null,
    val productCompanies: String,
    val genres: String,
    val releaseDate: String
)