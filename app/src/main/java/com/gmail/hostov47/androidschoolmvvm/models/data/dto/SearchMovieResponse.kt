/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SearchMovieResponse(
    @SerialName("page")
    var page: Int? = null,
    @SerialName("total_results")
    var totalResults: Int? = null,
    @SerialName("total_pages")
    var totalPages: Int? = null,
    @SerialName("results")
    var results: List<Movie>? = null
)