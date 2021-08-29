/**
 * Created by Ilia Shelkovenko on 28.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.di.profile.favorite

import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import com.gmail.hostov47.androidschoolmvvm.di.home.HomeModule
import com.gmail.hostov47.androidschoolmvvm.presentation.details.DetailsFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite.FavoriteMoviesFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FavoriteMoviesModule::class])
interface FavoriteMoviesComponent {
    fun inject(fragment: FavoriteMoviesFragment)
}