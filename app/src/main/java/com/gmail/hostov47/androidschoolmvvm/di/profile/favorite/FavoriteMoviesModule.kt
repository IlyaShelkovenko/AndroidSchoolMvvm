/**
 * Created by Ilia Shelkovenko on 28.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.profile.favorite

import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.FavoriteMoviesRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.FavoriteMoviesRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteMoviesModule {
    @Binds
    @FragmentScope
    abstract fun bindsFavoriteMoviesRepository(repository: FavoriteMoviesRepositoryImpl): FavoriteMoviesRepository
}