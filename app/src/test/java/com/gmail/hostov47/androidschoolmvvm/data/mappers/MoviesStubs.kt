/**
 * Created by Ilia Shelkovenko on 12.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MoviesResponse
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader


val appPath: String = File("").absolutePath
val filename = "$appPath\\src\\test\\java\\com\\gmail\\hostov47\\androidschoolmvvm\\data\\mappers\\MovieResponse"
val response: MoviesResponse = GsonBuilder().setPrettyPrinting().create().fromJson(FileReader(filename), MoviesResponse::class.java)

val moviesLocal: List<MovieLocal> = FromMovieToMovieLocalMapper.mapList(response.results)