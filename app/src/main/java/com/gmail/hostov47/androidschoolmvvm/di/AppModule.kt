/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApiImpl
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStoreImpl
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.utils.ConnectionCheckInterceptor
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProvider
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [AppBindModule::class]
)
class AppModule(private val appContext: ImdbApp) {

    @Provides
    @Singleton
    fun provideApp() = appContext

    @Provides
    @Singleton
    fun provideClient() =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ConnectionCheckInterceptor(context = appContext))
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Named("Caching")
    @Provides
    @Singleton
    fun provideSharedPreferences(appContext: ImdbApp): SharedPreferences {
        return appContext.getSharedPreferences(appContext.getString(R.string.PREFS_FOR_CASHING), Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideResources(appContext: ImdbApp): Resources = appContext.resources

    @Named("DefaultPrefs")
    @Provides
    @Singleton
    fun provideDefaultSharedPrefs(appContext: ImdbApp): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(appContext)


    @Provides
    @Singleton
    fun provideDb(appContext: ImdbApp): MovieDb {
        return Room.databaseBuilder(
            appContext,
            MovieDb::class.java,
            MovieDb.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(db: MovieDb) = db.favoriteMoviesDao()

    @Provides
    @Singleton
    fun provideWatchlistDao(db: MovieDb) = db.watchlistDao()
}

@Module
abstract class AppBindModule {
    @Binds
    @Singleton
    abstract fun bindsApi(api: ImdbApiImpl): ImdbApi

    @Binds
    @Singleton
    abstract fun bindsSchedulers(schedulers: SchedulersProviderImpl): SchedulersProvider

    @Binds
    @Singleton
    abstract fun bindsMovieStore(store: MovieStoreImpl): MovieStore
}