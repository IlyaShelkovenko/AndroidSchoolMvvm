/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.search

import com.gmail.hostov47.androidschoolmvvm.data.repository.search.SearchRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.search.SearchRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class SearchModule {
    @Binds
    @FragmentScope
    abstract fun bindsSearchMoviesRepository(repository: SearchRepositoryImpl): SearchRepository
}