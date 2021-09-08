/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.di.search

import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import com.gmail.hostov47.androidschoolmvvm.presentation.search.SearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}