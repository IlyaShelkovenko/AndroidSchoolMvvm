/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.domain

data class MovieDetailsDomain(
    val id: Int,
    val posterPath: String? = null,
    val title: String,
    val voteAverage: Double?,
    val overview: String? = null,
    val productCompanies: String,
    val genres: String,
    val releaseDate: String
){
    val rating: Float
        get() {
            return (voteAverage?.toFloat() ?: 0f) / 2
        }
}