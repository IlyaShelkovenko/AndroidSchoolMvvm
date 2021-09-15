/**
 * Created by Ilia Shelkovenko on 12.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MoviesResponse
import com.google.gson.GsonBuilder
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileReader


val json = Json {
    ignoreUnknownKeys = true
}
val appPath: String = File("").absolutePath
val filename = "$appPath\\src\\test\\java\\com\\gmail\\hostov47\\androidschoolmvvm\\data\\mappers\\MovieResponse"
val movieResponse: MoviesResponse = GsonBuilder().setPrettyPrinting().create().fromJson(FileReader(filename), MoviesResponse::class.java)

val moviesLocal: List<MovieLocal> = FromMovieToMovieLocalMapper.mapList(movieResponse.results)

fun getMovieDetails(): MovieDetailResponse {
    return json.decodeFromString("""
    {"adult":false,"backdrop_path":"/xXHZeb1yhJvnSHPzZDqee0zfMb6.jpg","belongs_to_collection":{"id":9485,"name":"Форсаж (Коллекция)","poster_path":"/r9Q3CZr5Z7s67OSaPdmAi3swk0B.jpg","backdrop_path":"/gC9BUFiROWtaMsluGYziZ6lR4OJ.jpg"},"budget":200000000,"genres":[{"id":28,"name":"боевик"},{"id":80,"name":"криминал"},{"id":53,"name":"триллер"}],"homepage":"https://www.universalpictures.ru/micro/fast9","id":385128,"imdb_id":"tt5433138","original_language":"en","original_title":"F9","overview":"Доминик Торетто ведет спокойную жизнь вместе с Летти и сыном Брайаном, но новая опасность всегда где-то рядом. В этот раз Доминику придется встретиться с призраками прошлого, если он хочет спасти самых близких. Команда снова собирается вместе, чтобы предотвратить дерзкий план по захвату мира, который придумал самый опасный преступник и безбашенный водитель из всех, с кем они сталкивались ранее. Ситуация усложняется тем, что этот человек — брат Доминика Джейкоб, которого много лет назад изгнали из семьи.","popularity":1066.52,"poster_path":"/mfccq2gRX7xDwzRxjrW6mUQSzD8.jpg","production_companies":[{"id":333,"logo_path":"/5xUJfzPZ8jWJUDzYtIeuPO4qPIa.png","name":"Original Film","origin_country":"US"},{"id":1225,"logo_path":"/rIxhJMR7oK8b2fMakmTfRLY2TZv.png","name":"One Race","origin_country":"US"},{"id":34530,"logo_path":null,"name":"Perfect Storm Entertainment","origin_country":"US"},{"id":154215,"logo_path":null,"name":"ZXY MOVIES","origin_country":""}],"production_countries":[{"iso_3166_1":"US","name":"United States of America"}],"release_date":"2021-05-19","revenue":714392860,"runtime":143,"spoken_languages":[{"english_name":"English","iso_639_1":"en","name":"English"},{"english_name":"French","iso_639_1":"fr","name":"Français"},{"english_name":"German","iso_639_1":"de","name":"Deutsch"},{"english_name":"Spanish","iso_639_1":"es","name":"Español"}],"status":"Released","tagline":"Быстрая семья навсегда","title":"Форсаж 9","video":false,"vote_average":7.5,"vote_count":3598}
""".trimIndent())
}