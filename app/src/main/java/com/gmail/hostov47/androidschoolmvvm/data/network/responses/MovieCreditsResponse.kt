/**
 * Created by Ilia Shelkovenko on 12.04.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieCreditsResponse(
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Crew>
)

@JsonClass(generateAdapter = true)
class Cast(
    val adult: Boolean,
    val gender: Long?,
    val id: Long,

    @Json(name = "known_for_department")
    val knownForDepartment: String,

    val name: String,

    @Json(name = "original_name")
    val originalName: String,

    val popularity: Double,

    @Json(name = "cast_id")
    val castId: Long,

    val character: String,

    @Json(name = "credit_id")
    val creditId: String,

    val order: Long,

    @Json(name = "profile_path")
    val profilePath: String?
)

@JsonClass(generateAdapter = true)
data class Crew(
    val adult: Boolean,
    val gender: Long?,
    val id: Long,

    @Json(name = "known_for_department")
    val knownForDepartment: String,

    val name: String,

    @Json(name = "original_name")
    val originalName: String,

    val popularity: Double,

    @Json(name = "profile_path")
    val profilePath: String?,

    @Json(name = "credit_id")
    val creditId: String,

    val department: String,
    val job: String
)