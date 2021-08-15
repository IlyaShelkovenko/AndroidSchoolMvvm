/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.home

import com.gmail.hostov47.androidschoolmvvm.data.repository.home.MoviesRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.home.MoviesRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {
    @Binds
    @FragmentScope
    abstract fun bindsMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository
}