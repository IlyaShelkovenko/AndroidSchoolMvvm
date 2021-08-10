/**
 * Created by Ilia Shelkovenko on 12.04.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsResponse(
    val id: Long,
    val cast: List<CastResponse>,
    val crew: List<Crew>
)

@Serializable
class CastResponse(
    val adult: Boolean,
    val gender: Long?,
    val id: Long,

    @SerialName("known_for_department")
    val knownForDepartment: String,

    val name: String,

    @SerialName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerialName("cast_id")
    val castId: Long,

    val character: String,

    @SerialName("credit_id")
    val creditId: String,

    val order: Long,

    @SerialName("profile_path")
    val profilePath: String?
)

@Serializable
data class Crew(
    val adult: Boolean,
    val gender: Long?,
    val id: Long,

    @SerialName("known_for_department")
    val knownForDepartment: String,

    val name: String,

    @SerialName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String?,

    @SerialName("credit_id")
    val creditId: String,

    val department: String,
    val job: String
)