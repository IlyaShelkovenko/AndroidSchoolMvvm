/**
 * Created by Ilia Shelkovenko on 15.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.di.details

import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.di.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class DetailsModule {
    @Binds
    @FragmentScope
    abstract fun bindsMovieDetailsRepository(repository: DetailsRepositoryImpl): DetailsRepository
}