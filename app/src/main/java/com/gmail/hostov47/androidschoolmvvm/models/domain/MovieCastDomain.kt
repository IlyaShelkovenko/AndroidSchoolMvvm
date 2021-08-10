/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.domain

import com.gmail.hostov47.androidschoolmvvm.BuildConfig

data class MovieCastDomain(
    val id: Long,
    val name: String,
    val profilePath: String?
){
    var fullPosterPath: String = ""
        get() {
            field = if(profilePath != null)
                "${ BuildConfig.POSTER_PATH}$profilePath"
            else ""
            return field
        }
}