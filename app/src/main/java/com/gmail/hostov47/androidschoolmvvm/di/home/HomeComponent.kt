/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.home

import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import dagger.Subcomponent


@FragmentScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}