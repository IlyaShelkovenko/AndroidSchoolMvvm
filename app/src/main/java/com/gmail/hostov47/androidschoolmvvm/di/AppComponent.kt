/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di

import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.di.details.DetailsComponent
import com.gmail.hostov47.androidschoolmvvm.di.home.HomeComponent
import com.gmail.hostov47.androidschoolmvvm.di.profile.favorite.FavoriteMoviesComponent
import com.gmail.hostov47.androidschoolmvvm.di.profile.watchlist.WatchListComponent
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.watchlist.WatchListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class], )
interface AppComponent {
    fun inject(app: ImdbApp)
    fun getHomeComponent(): HomeComponent
    fun getDetailsComponent(): DetailsComponent
    fun getFavoriteMoviesComponent(): FavoriteMoviesComponent
    fun getWatchListComponent(): WatchListComponent
}