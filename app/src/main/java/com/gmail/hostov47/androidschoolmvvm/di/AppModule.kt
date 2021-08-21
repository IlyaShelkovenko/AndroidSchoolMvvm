/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.preference.PreferenceManager
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApiImpl
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStoreImpl
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [AppBindModule::class])
class AppModule(private val appContext: ImdbApp) {

    @Provides
    @Singleton
    fun provideApp() = appContext

    @Provides
    @Singleton
    fun provideClient() =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
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
        return appContext.getSharedPreferences("prefs_for_caching", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideResources(appContext: ImdbApp): Resources = appContext.resources

    @Named("DefaultPrefs")
    @Provides
    @Singleton
    fun provideDefaultSharedPrefs(appContext: ImdbApp): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)

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