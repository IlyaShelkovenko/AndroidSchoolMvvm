/**
 * Created by Ilia Shelkovenko on 29.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.di.profile.watchlist

import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import com.gmail.hostov47.androidschoolmvvm.di.profile.favorite.FavoriteMoviesModule
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite.FavoriteMoviesFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.watchlist.WatchListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [WatchListModule::class])
interface WatchListComponent {
    fun inject(fragment: WatchListFragment)
}