/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.details

import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import com.gmail.hostov47.androidschoolmvvm.presentation.details.DetailsFragment
import dagger.Subcomponent


@FragmentScope
@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    fun inject(fragment: DetailsFragment)
}