/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.data.local

import kotlinx.serialization.Serializable


@Serializable
data class MovieCastLocal(
    val id: Long,
    val name: String,
    val profilePath: String?
)