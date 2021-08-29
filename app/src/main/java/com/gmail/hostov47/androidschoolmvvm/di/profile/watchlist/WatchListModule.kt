/**
 * Created by Ilia Shelkovenko on 29.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.profile.watchlist

import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.WatchListRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.WatchListRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class WatchListModule {
    @Binds
    @FragmentScope
    abstract fun bindsWatchListRepository(repository: WatchListRepositoryImpl): WatchListRepository
}